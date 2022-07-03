package com.hj.blog.admin.vo.params;

import com.hj.blog.admin.vo.CategoryVo;
import com.hj.blog.admin.vo.TagVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParams {
    private Long id;
    private ArticleBodyParams body;
    private String summary;
    private CategoryVo category;
    private List<TagVo> tags;

    private String title;
    private String search;
}
