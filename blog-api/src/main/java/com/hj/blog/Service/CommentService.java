package com.hj.blog.Service;

import com.hj.blog.admin.vo.Result;
import com.hj.blog.admin.vo.params.CommentParams;

public interface CommentService {
    Result findCommentsByArticleId(Long articleId);

    Result comment(CommentParams commentParams);

}
