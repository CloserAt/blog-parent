package com.hj.blog.Service.serviceImpl;

import com.alibaba.fastjson.support.odps.udf.CodecCheck;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hj.blog.Service.ArticleService;
import com.hj.blog.Service.SysUserService;
import com.hj.blog.Service.TagService;
import com.hj.blog.dao.dos.Archives;
import com.hj.blog.dao.mapper.ArticleMapper;
import com.hj.blog.dao.pojo.Article;
import com.hj.blog.dao.pojo.SysUser;
import com.hj.blog.vo.ArticleVo;
import com.hj.blog.vo.Result;
import com.hj.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result listArticle(PageParams pageParams) {
        /*
        分页查询article数据库表
         */
        int pageNumber = pageParams.getPage();
        int pageSize = pageParams.getPageSize();
        Page<Article> page = new Page<>(pageNumber,pageSize);

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getWeight);//按照是否置顶排序
        queryWrapper.orderByDesc(Article::getCreateData);//按照创建时间倒叙排列

        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);//第一个参数为查询的页，第二个为查询条件
        List<Article> records = articlePage.getRecords();
        List<ArticleVo>  articleVoList = copyList(records,true,true);
        return Result.success(articleVoList);
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isAuthor, boolean isTags) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            articleVoList.add(copy(records.get(i),true,true));
        }
        return  articleVoList;
    }

    private ArticleVo copy(Article article, boolean isAuthor, boolean isTags) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);//spring提供的copy方法，将article的数据拷贝到articleVo中
        //因为article中的createDate是Long型，而articleVo中的是String类型，所以此处需要转一下类型，不然copy不过来
        articleVo.setCrateDate(new DateTime(article.getCreateData()).toString("yyyy-MM-dd HH:mm"));
        //并不是所有接口都需要标签和作者的信息，此处需要两个处理
        if (isAuthor) {
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findAuthorsByAuthorId(authorId).getNickname());
        }
        if (isTags) {
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        return articleVo;
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
        queryWrapper.orderByDesc(Article::getCreateData);//根据创建时间倒叙排序
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
}
