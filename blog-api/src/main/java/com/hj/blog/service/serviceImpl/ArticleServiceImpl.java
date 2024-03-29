package com.hj.blog.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hj.blog.service.*;
import com.hj.blog.admin.dao.dos.Archives;
import com.hj.blog.admin.mapper.ArticleBodyMapper;
import com.hj.blog.admin.mapper.ArticleMapper;
import com.hj.blog.admin.mapper.ArticleTagMapper;
import com.hj.blog.admin.pojo.*;
import com.hj.blog.utils.UserThreadLocal;
import com.hj.blog.admin.vo.*;
import com.hj.blog.admin.vo.params.ArticleParams;
import com.hj.blog.admin.vo.params.PageParams;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate redisTemplate;

//    @Autowired
//    private RocketMQTemplate rocketMQTemplate;

//    @Override
//    public Result listArticle(PageParams pageParams) {
//        int pageNumber = pageParams.getPage();
//        int pageSize = pageParams.getPageSize();
//        Page<Article> page = new Page<>(pageNumber,pageSize);
//        IPage<Article> articleIPage = articleMapper.listArticle(
//                page,
//                pageParams.getCategoryId(),
//                pageParams.getTagId(),
//                pageParams.getYear(),
//                pageParams.getMonth());
//        List<Article> records = articleIPage.getRecords();
//        for (Article record : records) {
//            String viewCount = (String) redisTemplate.opsForHash().get("view_count", String.valueOf(record.getId()));
//            if (viewCount != null){
//                record.setViewCounts(Integer.parseInt(viewCount));
//            }
//        }
//        return Result.success(copyList(records,true,true));
//    }

    @Override
    public Result listArticle(PageParams pageParams) {
        /*
        分页查询article数据库表
         */
        int pageNumber = pageParams.getPage();
        int pageSize = pageParams.getPageSize();
        Page<Article> page = new Page<>(pageNumber,pageSize);

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (pageParams.getCategoryId() != null) {
            //and category_id=#{categoryId}
            queryWrapper.eq(Article::getCategoryId, pageParams.getCategoryId());
        }
        List<Long> articleIdList = new ArrayList<>();
        if (pageParams.getTagId() != null) {
            //加入标签 条件查询
            //article表中没有tag字段 一篇文章 有多个标签
            //article_tag article_id 1:n tag_id的对应关系
            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId, pageParams.getTagId());
            List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
            for (ArticleTag articleTag : articleTags) {
                articleIdList.add(articleTag.getId());
            }
            if (articleIdList.size() > 0) {
                queryWrapper.in(Article::getId, articleIdList);
            }
        }

        queryWrapper.orderByDesc(Article::getWeight);//按照是否置顶排序
        queryWrapper.orderByDesc(Article::getCreateDate);//按照创建时间倒叙排列

        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);//第一个参数为查询的页，第二个为查询条件
        List<Article> records = articlePage.getRecords();
        List<ArticleVo>  articleVoList = copyList(records,true,true);
        return Result.success(articleVoList);
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isAuthor, boolean isTag) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isAuthor,isTag,false,false));
        }
        return  articleVoList;
    }
    //重载的方式
    private List<ArticleVo> copyList(List<Article> records, boolean isAuthor, boolean isTag, boolean isBody, boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isAuthor,isTag,isBody,isCategory));
        }
        return articleVoList;
    }


    @Autowired
    private CategoryService categoryService;

    private ArticleVo copy(Article article, boolean isAuthor, boolean isTag, boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));//String.valueOf()可以避免空指针异常
        BeanUtils.copyProperties(article,articleVo);//spring提供的copy方法，将article的数据拷贝到articleVo中

        //因为article中的createDate是Long型，而articleVo中的是String类型，所以此处需要转一下类型，不然copy不过来
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));

        //并不是所有接口都需要标签和作者的信息，此处需要两个处理
        if (isAuthor) {
//            Long authorId = article.getAuthorId();
//            articleVo.setAuthor(sysUserService.findAuthorsByAuthorId(authorId).getNickname());
            Long authorId = article.getAuthorId();
            SysUser sysUser = sysUserService.findAuthorsByAuthorId(authorId);
            UserVo userVo = new UserVo();
            userVo.setAvatar(sysUser.getAvatar());
            userVo.setId(sysUser.getId().toString());
            userVo.setNickname(sysUser.getNickname());
            articleVo.setAuthor(userVo);
        }
        if (isTag) {
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isBody) {
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyByBodyId(bodyId));
        }
        if (isCategory) {
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryByCategoryId(categoryId));
        }
        return articleVo;
    }

    @Autowired
    private ArticleBodyMapper articleBodyMapper;


    private ArticleBodyVo findArticleBodyByBodyId(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }


    //最热文章接口实现
    @Override
    public Result listHotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);//根据浏览量倒叙排序
        queryWrapper.select(Article::getId,Article::getTitle);//根据接口说明只需要id，title这两个数据，因此此处需要选择以下
        queryWrapper.last("limit " + limit);//限制数量等价于 select id,title from article order by view_counts desc limit 5

        List<Article> articleList = articleMapper.selectList(queryWrapper);//将排完序的结果放入集合
        return Result.success(copyList(articleList,false,false));
    }


    //最新文章接口实现
    @Override
    public Result listNewArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);//根据创建时间倒叙排序
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit " + limit);

        List<Article> articleList = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articleList,false,false));
    }


    //文章归档接口实现
    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }


    @Autowired
    private ThreadService threadService;

    //文章详情接口实现
    @Override
    public Result findArticleById(Long articleId) {
        /*
          1.根据id查询 文章信息
          2.根据bodyId和categoryId关联查询
         */
        Article article = this.articleMapper.selectById(articleId);//根据文章id找到文章
        ArticleVo articleVo = copy(article, true, true,true,true);//再将article转换为articleVo
        //如果在此处加上一个查看玩文章后更新阅读数的功能的话，是不太建议的
        //因为在查看玩文章之后，本应该直接返回数据了，此时做了一个更新操作，更新世在数据库会加写锁，阻塞其他的读操作，会降低性能
        //更新也会增加此次接口的耗时，且一旦更新出了问题，不能影响查看文章的操作
        //所以可以采用 线程池 中放入更新操作，就和主线程不相关了
        threadService.updateArticleViewCount(articleMapper,article);

        String viewCount = (String) redisTemplate.opsForHash().get("view_count", String.valueOf(articleId));
        if (viewCount != null){
            articleVo.setViewCounts(Integer.parseInt(viewCount));
        }
        return Result.success(articleVo);
    }

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public Result articlePublish(ArticleParams articleParams) {

        /*
        1.构建Article对象
        2.获取作者信息的id
        3.将标签加入到关联列表中
        4.body内容存储
        */
        SysUser sysUser = UserThreadLocal.get();//获取作者信息

        Article article = new Article();
        boolean isEdit = false;
        //如果穿了id，说明不是新增，而是编辑选项过来的
        if (articleParams.getId() != null) {
            article = new Article();
            article.setId(articleParams.getId());
            article.setTitle(articleParams.getTitle());
            article.setSummary(articleParams.getSummary());
            article.setCategoryId(Long.parseLong(articleParams.getCategory().getId()));
            articleMapper.updateById(article);
            isEdit = true;
        } else {
            article = new Article();
            article.setAuthorId(sysUser.getId());//从作者信息中获取作者id来设置
            article.setWeight(Article.Article_Common);
            article.setViewCounts(0);
            article.setTitle(articleParams.getTitle());
            article.setSummary(articleParams.getSummary());
            article.setCommentCounts(0);
            article.setCreateDate(System.currentTimeMillis());
            article.setCategoryId(Long.parseLong(articleParams.getCategory().getId()));
            this.articleMapper.insert(article);//插入之后会生成一个文章id
        }

        //将标签加入到关联列表中
        List<TagVo> tags = articleParams.getTags();
        if (tags != null) {
            for (TagVo tag : tags) {
                Long articleId = article.getId();
                if (isEdit){
                    //先删除
                    LambdaQueryWrapper<ArticleTag> queryWrapper = Wrappers.lambdaQuery();
                    queryWrapper.eq(ArticleTag::getArticleId,articleId);
                    articleTagMapper.delete(queryWrapper);
                }
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(Long.parseLong(tag.getId()));
                articleTag.setArticleId(articleId);
                articleTagMapper.insert(articleTag);
            }
        }

        //body内容往数据库存储
        if (isEdit){
            ArticleBody articleBody = new ArticleBody();
            articleBody.setArticleId(article.getId());
            articleBody.setContent(articleParams.getBody().getContent());
            articleBody.setContentHtml(articleParams.getBody().getContentHtml());
            LambdaUpdateWrapper<ArticleBody> updateWrapper = Wrappers.lambdaUpdate();
            updateWrapper.eq(ArticleBody::getArticleId,article.getId());
            articleBodyMapper.update(articleBody, updateWrapper);
        }else {
            ArticleBody articleBody = new ArticleBody();
            articleBody.setArticleId(article.getId());
            articleBody.setContent(articleParams.getBody().getContent());
            articleBody.setContentHtml(articleParams.getBody().getContentHtml());
            articleBodyMapper.insert(articleBody);

            article.setBodyId(articleBody.getId());
            articleMapper.updateById(article);//更新
        }

        Map<String,String> map = new HashMap<>();//接口说明中返回的是{"id":12232323}
        map.put("id", article.getId().toString());//此处转为String也是防止精度损失

//        if (isEdit) {
//            //发送一条消息给rocketmq 当前文章更新了，更新一下缓存吧
//            ArticleMessage articleMessage = new ArticleMessage();
//            articleMessage.setArticleId(article.getId());
//            rocketMQTemplate.convertAndSend("blog-update-article",articleMessage);//把articleMessage发送到了队列中了
//        }

        return Result.success(map);
    }

    @Override
    public Result searchArticle(String search) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.like(Article::getTitle,search);//模糊搜索
        //select id,title from article order by view_counts desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles,false,false));
    }
}
