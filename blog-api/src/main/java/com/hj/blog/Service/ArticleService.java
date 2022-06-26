package com.hj.blog.Service;

import com.hj.blog.vo.Result;
import com.hj.blog.vo.params.PageParams;
import org.springframework.stereotype.Service;


public interface ArticleService {
    Result listArticle(PageParams pageParams);

    Result listHotArticle(int limit);

    Result listNewArticle(int limit);

    Result listArchives();
}
