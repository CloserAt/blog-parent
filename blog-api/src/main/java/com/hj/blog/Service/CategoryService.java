package com.hj.blog.Service;

import com.hj.blog.vo.CategoryVo;

public interface CategoryService {
    CategoryVo findCategoryByCategoryId(Long categoryId);
}
