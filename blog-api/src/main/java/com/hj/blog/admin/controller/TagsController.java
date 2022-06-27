package com.hj.blog.admin.controller;

import com.hj.blog.Service.TagService;
import com.hj.blog.admin.vo.Result;
import com.hj.blog.admin.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
接口url：/tags/hot

请求方式：GET

请求参数：无

返回数据：

~~~json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": [
        {
            "id":1,
            "tagName":"4444"
        }
    ]
}
~~~
 */
//最热标签接口
@RestController
@RequestMapping("tags")
public class TagsController {
    @Autowired
    private TagService tagService;

    @GetMapping("/hot")// : /tags/hot
    public Result listHotTags() {
        return tagService.hots(5);//此处5是设置的默认前五个最热标签-linmit
    }

    //所有标签接口
    /*
    接口url：/tags

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
                "id": 5,
                "tagName": "springboot"
            },
            {
                "id": 6,
                "tagName": "spring"
            },
            {
                "id": 7,
                "tagName": "springmvc"
            },
            {
                "id": 8,
                "tagName": "11"
            }
        ]
    }
    ~~~
     */
    @GetMapping
    public Result tagsAll() {
        return tagService.findAllTags();
    }



    //查看所有标签接口
    @GetMapping("/detail")
    public Result findAllTagsDetail() {
        return tagService.findAllTagsDetail();
    }



    //标签文章列表接口
    @GetMapping("/detail/{id}")
    public Result findTagsDetailById(@PathVariable("id") Long id) {
        return tagService.findTagsDetailById(id);
    }
}
