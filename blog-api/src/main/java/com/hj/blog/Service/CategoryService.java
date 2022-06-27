package com.hj.blog.Service;

import com.hj.blog.admin.vo.CategoryVo;
import com.hj.blog.admin.vo.Result;

public interface CategoryService {
    CategoryVo findCategoryByCategoryId(Long categoryId);

    Result findCategory();

    Result findAllCategoriesDetail();

    Result findCategoriesDetailById(Long id);

}
