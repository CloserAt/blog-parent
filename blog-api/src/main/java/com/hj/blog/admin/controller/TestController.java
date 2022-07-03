package com.hj.blog.admin.controller;

import com.hj.blog.admin.pojo.SysUser;
import com.hj.blog.admin.vo.Result;
import com.hj.blog.admin.vo.TagVo;
import com.hj.blog.admin.vo.params.ArticleParams;
import com.hj.blog.utils.UserThreadLocal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(@RequestBody ArticleParams articleParams) {
        List<TagVo> tags = articleParams.getTags();
        for (TagVo tag : tags) {
            if (tag.getId() == null) {
                System.out.println(true);
            }
        }
        return Result.success(null);
    }
}
