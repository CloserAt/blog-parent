package com.hj.blog.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hj.blog.service.CommentService;
import com.hj.blog.service.SysUserService;
import com.hj.blog.admin.mapper.CommentMapper;
import com.hj.blog.admin.pojo.Comment;
import com.hj.blog.admin.pojo.SysUser;
import com.hj.blog.utils.UserThreadLocal;
import com.hj.blog.admin.vo.CommentVo;
import com.hj.blog.admin.vo.Result;
import com.hj.blog.admin.vo.UserVo;
import com.hj.blog.admin.vo.params.CommentParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result findCommentsByArticleId(Long articleId) {
        /*
        1.根据文章id从 comment表 查询评论列表
        2.根据作者id从 User表中 查询作者信息
        3.判断如果level=1要去查询它有没有子评论
            有 ，根据评论id（parentId）进行查询
         */
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //首先查询level=1的评论
        queryWrapper.eq(Comment::getArticleId, articleId);
        queryWrapper.eq(Comment::getLevel,1);

        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVoList = copyList(comments);
        return Result.success(commentVoList);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    /*
    private ArticleVo copy(Article article, boolean isAuthor, boolean isTags, boolean isBody, boolean isCategory) {
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
     */
    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        commentVo.setId(String.valueOf(comment.getId()));

        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        Long authorId = comment.getAuthorId();

        //作者信息
        UserVo userVo = this.sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);

        //子评论,等于1才有子评论
        if (comment.getLevel() == 1) {
            Long parentId = comment.getId();
            List<CommentVo> commentVoList = findCommentsByParentId(parentId);
            commentVo.setChildrens(commentVoList);
        }

        //toUser
        if (comment.getLevel() > 1) {
            Long toUid = comment.getToUid();
            UserVo toUserVo = this.sysUserService.findUserVoById(toUid);
            commentVo.setToUser(toUserVo);
        }

        return commentVo;
    }

    private List<CommentVo> findCommentsByParentId(Long parentId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,parentId);
        queryWrapper.eq(Comment::getLevel, 2);
        return copyList(commentMapper.selectList(queryWrapper));
    }


    //评论功能接口实现
    @Override
    public Result comment(CommentParams commentParams) {
        SysUser sysUser = UserThreadLocal.get();//从这里获取登陆的用户信息

        Comment comment = new Comment();
        comment.setArticleId(commentParams.getArticleId());
        comment.setAuthorId(sysUser.getId());//空指针异常
        comment.setContent(commentParams.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParams.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        } else {
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParams.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.commentMapper.insert(comment);

        CommentVo commentVo = copy(comment);
        return Result.success(commentVo);
    }
}
