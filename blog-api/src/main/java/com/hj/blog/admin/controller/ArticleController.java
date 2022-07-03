package com.hj.blog.admin.controller;

import com.hj.blog.service.ArticleService;
import com.hj.blog.common.aop.LogAnnotation;
import com.hj.blog.admin.vo.Result;
import com.hj.blog.admin.vo.params.ArticleParams;
import com.hj.blog.admin.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController//json数据进行交互
@RequestMapping("articles")//请求网址: http://localhost:8888/articles 路径请求是articles,所以注解中的括号填写articles
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //首页文章列表接口
    /*
    首页 文章列表
    依据开发日志，返回值是：
        "success": true,
        "code": 200,
        "msg": "success",
        "data": [
     */
    @PostMapping
    @LogAnnotation(module = "文章",operator = "获取文章列表")
    //@CacheAnnotation(expire = 5 * 60 * 1000,name = "listArticle")
    //返回值Result来自service层的方法所查询到的数据
    public Result listArticle(@RequestBody PageParams pageParams) {
        //pageParams需要查询的数据
        return articleService.listArticle(pageParams);
    }

    //最热文章-接口
    /*
    接口url：/articles/hot

    请求方式：POST

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
                "title": "springboot介绍以及入门案例",
            },
            {
                "id": 9,
                "title": "Vue.js 是什么",
            },
            {
                "id": 10,
                "title": "Element相关",
            }
        ]
    }
     */
    @PostMapping("hot")
    //@CacheAnnotation(expire = 5*60*1000, name = "hot_article")//统一缓存处理的注解
    public Result listHotArticle() {
        return articleService.listHotArticle(5);
    }


    //最新文章-接口
    /*
    接口url：/articles/new

    请求方式：POST

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
                "title": "springboot介绍以及入门案例",
            },
            {
                "id": 9,
                "title": "Vue.js 是什么",
            },
            {
                "id": 10,
                "title": "Element相关",

            }
        ]
    }
    ~~~
     */
    @PostMapping("new")
    public Result listNewArticle() {
        return articleService.listNewArticle(5);
    }


    //文章归档-接口
    /*
    接口url：/articles/listArchives

    请求方式：POST

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
                "year": "2021",
                "month": "6",
                "count": 2
            }

        ]
    }
    ~~~
     */
    @PostMapping("listArchives")
    public Result listArchives() {
        return articleService.listArchives();
    }


    //文章详情接口
    /*
    接口url：/article/view/{id}

    请求方式：POST

    请求参数：

    | 参数名称 | 参数类型 | 说明               |
    | -------- | -------- | ------------------ |
    | id       | long     | 文章id（路径参数） |
    |          |          |                    |
    |          |          |                    |

    返回数据：

    ~~~json
    {
        "success": true,
        "code": 200,
        "msg": "success",
        "data": "token"
    }
    ~~~
     */
    @PostMapping("view/{id}")
    public Result articleBody(@PathVariable("id") Long articleId) {
        return articleService.findArticleById(articleId);
    }


    //文章发布接口
    /*
    接口url：/articles/publish

    请求方式：POST

    请求参数：

    | 参数名称 | 参数类型                                                    | 说明               |
    | -------- | ----------------------------------------------------------- | ------------------ |
    | title    | string                                                      | 文章标题           |
    | id       | long                                                        | 文章id（编辑有值） |
    | body     | object（{content: "ww", contentHtml: "<p>ww</p>↵"}）        | 文章内容           |
    | category | {id: 2, avatar: "/category/back.png", categoryName: "后端"} | 文章类别           |
    | summary  | string                                                      | 文章概述           |
    | tags     | [{id: 5}, {id: 6}]                                          | 文章标签           |

    返回数据：

    ~~~json
    {
        "success": true,
        "code": 200,
        "msg": "success",
        "data": {"id":12232323}
    }
    ~~~
     */
    //注意此接口也需要加入到拦截路径中
    @PostMapping("publish")
    public Result articlePublish(@RequestBody ArticleParams articleParams) {
        return articleService.articlePublish(articleParams);
    }

    @PostMapping("{id}")
    public Result articleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }


    //搜索文章接口
    @PostMapping("search")
    public Result search(@RequestBody ArticleParams articleParams){
        //写一个搜索接口
        String search = articleParams.getSearch();
        return articleService.searchArticle(search);
    }
}
