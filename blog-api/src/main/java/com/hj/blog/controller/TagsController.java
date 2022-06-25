package com.hj.blog.controller;

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
@RestController
@RequestMapping("/tags/hot")
public class TagsController {

}
