package com.hj.blog.controller;

import com.hj.blog.Service.CommentService;
import com.hj.blog.vo.Result;
import com.hj.blog.vo.params.CommentParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //评论列表接口
    /*
    接口url：/comments/article/{id}

    请求方式：GET

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
        "data": [
            {
                "id": 53,
                "author": {
                    "nickname": "李四",
                    "avatar": "http://localhost:8080/static/img/logo.b3a48c0.png",
                    "id": 1
                },
                "content": "写的好",
                "childrens": [
                    {
                        "id": 54,
                        "author": {
                            "nickname": "李四",
                            "avatar": "http://localhost:8080/static/img/logo.b3a48c0.png",
                            "id": 1
                        },
                        "content": "111",
                        "childrens": [],
                        "createDate": "1973-11-26 08:52",
                        "level": 2,
                        "toUser": {
                            "nickname": "李四",
                            "avatar": "http://localhost:8080/static/img/logo.b3a48c0.png",
                            "id": 1
                        }
                    }
                ],
                "createDate": "1973-11-27 09:53",
                "level": 1,
                "toUser": null
            }
        ]
    }
     */
    @PostMapping("/article/{id}")
    public Result comments(@PathVariable("id") Long articleId) {
        return commentService.findCommentsByArticleId(articleId);
    }

    //评论功能接口
    /*
    接口url：/comments/create/change

    请求方式：POST

    请求参数：

    | 参数名称  | 参数类型 | 说明           |
    | --------- | -------- | -------------- |
    | articleId | long     | 文章id         |
    | content   | string   | 评论内容       |
    | parent    | long     | 父评论id       |
    | toUserId  | long     | 被评论的用户id |

    返回数据：

    ~~~json
    {
        "success": true,
        "code": 200,
        "msg": "success",
        "data": null
    }
     */
    @PostMapping("/create/change")
    public Result comment(@RequestBody CommentParams commentParams) {
        return commentService.comment(commentParams);
    }
}
