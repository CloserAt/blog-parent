package com.hj.blog.controller;

import com.hj.blog.Service.ArticleService;
import com.hj.blog.vo.Result;
import com.hj.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
接口url：/articles

请求方式：POST

请求参数：

| 参数名称 | 参数类型 | 说明           |
| -------- | -------- | -------------- |
| page     | int      | 当前页数       |
| pageSize | int      | 每页显示的数量 |
|          |          |                |

返回数据：

~~~json
 */
@RestController//json数据进行交互
@RequestMapping("articles")//请求网址: http://localhost:8888/articles 路径请求是articles,所以注解中的括号填写articles
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /*
    首页 文章列表
    依据开发日志，返回值是：
        "success": true,
        "code": 200,
        "msg": "success",
        "data": [
     */
    @PostMapping("/articles")
    //返回值Result来自service层的方法所查询到的数据
    public Result listArticle(@RequestBody PageParams pageParams) {
        //pageParams需要查询的数据
        return articleService.listArticle(pageParams);
    }
}
