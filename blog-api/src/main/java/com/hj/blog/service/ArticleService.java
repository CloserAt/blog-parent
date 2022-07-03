package com.hj.blog.service;

import com.hj.blog.admin.vo.Result;
import com.hj.blog.admin.vo.params.ArticleParams;
import com.hj.blog.admin.vo.params.PageParams;


public interface ArticleService {
    Result listArticle(PageParams pageParams);

    Result listHotArticle(int limit);

    Result listNewArticle(int limit);

    Result listArchives();

    Result findArticleById(Long articleId);

    Result articlePublish(ArticleParams articleParams);

    Result searchArticle(String search);
}
