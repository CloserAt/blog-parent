package com.hj.blog.service;

import com.hj.blog.admin.vo.Result;
import com.hj.blog.admin.vo.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagsByArticleId(Long articleId);//根据文章id查询标签列表

    Result hots(int i);

    Result findAllTags();

    Result findAllTagsDetail();

    Result findTagsDetailById(Long id);

}
