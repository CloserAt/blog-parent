package com.hj.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hj.blog.dao.pojo.Category;
import com.hj.blog.vo.CategoryVo;
import io.lettuce.core.dynamic.batch.BatchExecutor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
