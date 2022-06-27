package com.hj.blog.handler;

import com.hj.blog.admin.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//针对加了@Controller注解的方法进行拦截处理，本质就是个aop的实现
@ControllerAdvice
public class AllExceptionHandler {

    //进行异常处理，处理Exception.class的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody // 返回json数据
    public Result doException(Exception e) {
        e.printStackTrace();
        return Result.fail(-999,"系统异常");
    }
}
