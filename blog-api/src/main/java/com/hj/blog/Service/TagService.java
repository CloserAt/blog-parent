package com.hj.blog.Service;

import com.hj.blog.vo.Result;
import com.hj.blog.vo.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagsByArticleId(Long articleId);//根据文章id查询标签列表

    Result hots(int i);

    Result findAllTags();

}
