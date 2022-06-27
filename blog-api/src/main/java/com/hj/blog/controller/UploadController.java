package com.hj.blog.controller;

import com.hj.blog.utils.QiniuUtils;
import com.hj.blog.vo.ErrorCode;
import com.hj.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("upload")
public class UploadController {

    //文件上传接口
/*
接口url：/upload

请求方式：POST

请求参数：

| 参数名称 | 参数类型 | 说明           |
| -------- | -------- | -------------- |
| image    | file     | 上传的文件名称 |
|          |          |                |
|          |          |                |

返回数据：

~~~json
{
    "success":true,
 	"code":200,
    "msg":"success",
    "data":"https://static.mszlu.com/aa.png"
}
~~~
 */
    //MultipartFile spring中专门接受文件的类型

    @Autowired
    private QiniuUtils qiniuUtils;

    @PostMapping
    public Result uploadPicture(@RequestParam("image") MultipartFile file) {
        //原始文件名称
        String originalFilename = file.getOriginalFilename();
        //切割得到后缀后，再加上随机字符串得到唯一文件名称
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        //上传文件上传至七牛云服务器
        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload) {
            return Result.success(QiniuUtils.url + fileName);
        }
        return Result.fail(ErrorCode.UPLOAD_FAIL.getCode(), ErrorCode.UPLOAD_FAIL.getMsg());
    }
}
