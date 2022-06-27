package com.hj.blog.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hj.blog.admin.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    List<Tag> findTagsByArticleId(Long articleId);

    List<Long> findHotTagIds(int i);//查询最热标签的前n条

    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
