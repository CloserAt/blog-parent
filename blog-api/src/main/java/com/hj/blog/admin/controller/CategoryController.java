package com.hj.blog.admin.controller;

import com.hj.blog.service.CategoryService;
import com.hj.blog.admin.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categorys")
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
        return categoryService.findCategoryAll();
    }


    //导航详细文章分类接口
    /*
    接口url：/categorys/detail

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
        "success": true,
        "code": 200,
        "msg": "success",
        "data": [
            {
                "id": 1,
                "avatar": "/static/category/front.png",
                "categoryName": "前端",
                "description": "前端是什么，大前端"
            },
            {
                "id": 2,
                "avatar": "/static/category/back.png",
                "categoryName": "后端",
                "description": "后端最牛叉"
            },
            {
                "id": 3,
                "avatar": "/static/category/lift.jpg",
                "categoryName": "生活",
                "description": "生活趣事"
            },
            {
                "id": 4,
                "avatar": "/static/category/database.png",
                "categoryName": "数据库",
                "description": "没数据库，啥也不管用"
            },
            {
                "id": 5,
                "avatar": "/static/category/language.png",
                "categoryName": "编程语言",
                "description": "好多语言，该学哪个？"
            }
        ]
    }

    ~~~
     */
    @GetMapping("detail")
    public Result findAllCategoriesDetail() {
        return categoryService.findAllCategoriesDetail();
    }


    //分类文章列表接口
    /*
    接口url：/category/detail/{id}

    请求方式：GET

    请求参数：

    | 参数名称 | 参数类型 | 说明     |
    | -------- | -------- | -------- |
    | id       | 分类id   | 路径参数 |
    |          |          |          |
    |          |          |          |

    返回数据：

    ~~~json
    {
        "success": true,
        "code": 200,
        "msg": "success",
        "data":
            {
                "id": 1,
                "avatar": "/static/category/front.png",
                "categoryName": "前端",
                "description": "前端是什么，大前端"
            }
    }
     */
    @GetMapping("detail/{id}")
    public Result findCategoriesDetailById(@PathVariable("id") Long id) {
        return categoryService.findCategoriesDetailById(id);
    }
}
