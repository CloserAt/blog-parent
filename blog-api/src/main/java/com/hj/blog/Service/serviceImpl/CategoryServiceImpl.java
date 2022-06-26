package com.hj.blog.Service.serviceImpl;

import com.hj.blog.Service.CategoryService;
import com.hj.blog.dao.mapper.CategoryMapper;
import com.hj.blog.dao.pojo.Category;
import com.hj.blog.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryVo findCategoryByCategoryId(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return null;
    }
}
