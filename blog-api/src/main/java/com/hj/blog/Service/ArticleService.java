package com.hj.blog.Service;

import com.hj.blog.vo.Result;
import com.hj.blog.vo.params.PageParams;
import org.springframework.stereotype.Service;


public interface ArticleService {
    Result listArticle(PageParams pageParams);
}
