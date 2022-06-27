package com.hj.blog.Service;

import com.hj.blog.vo.CategoryVo;
import com.hj.blog.vo.Result;

public interface CategoryService {
    CategoryVo findCategoryByCategoryId(Long categoryId);

    Result findCategory();

    Result findAllCategoriesDetail();

    Result findCategoriesDetailById(Long id);

}
