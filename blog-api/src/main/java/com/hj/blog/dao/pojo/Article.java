package com.hj.blog.dao.pojo;

import lombok.Data;
import org.springframework.context.annotation.Bean;

@Data
public class Article {
    public static final int Article_Common = 0;
    public static final int Article_TOP = 1;
    private Long id;
    private Integer commentCounts;
    private Long createDate;
    private String summary;
    private String title;
    private Integer viewCounts;
    private Integer weight;
    private Long authorId;
    private Long bodyId;
    private Long categoryId;
}
