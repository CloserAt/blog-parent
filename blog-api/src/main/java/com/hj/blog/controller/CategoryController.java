package com.hj.blog.controller;

import com.hj.blog.Service.CategoryService;
import com.hj.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    //所有分类接口
    /*
    接口url：/categorys
    请求方式：GET

    请求参数：

    | 参数名称 | 参数类型 | 说明 |
    | -------- | -------- | ---- |
    |          |          |      |
    |          |          |      |
    |          |          |      |

    返回数据：

    ~~~json
    {
        "success":true,
        "code":200,
        "msg":"success",
        "data":
        [
            {"id":1,"avatar":"/category/front.png","categoryName":"前端"},
            {"id":2,"avatar":"/category/back.png","categoryName":"后端"},
            {"id":3,"avatar":"/category/lift.jpg","categoryName":"生活"},
            {"id":4,"avatar":"/category/database.png","categoryName":"数据库"},
            {"id":5,"avatar":"/category/language.png","categoryName":"编程语言"}
        ]
    }
     */
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result categoryAll() {
        return categoryService.findCategory();
    }
}
