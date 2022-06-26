package com.hj.blog.vo.params;

import com.hj.blog.vo.CategoryVo;
import com.hj.blog.vo.TagVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParams {
    private Long id;
    private ArticleBodyParams body;
    private String title;
    private String summary;
    private CategoryVo category;
    private List<TagVo> tags;
}
