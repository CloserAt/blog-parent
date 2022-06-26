package com.hj.blog.common.aop;

import java.lang.annotation.*;

//Type代表该注解可以放在类上，METHOD代表可以放在方法上
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";
    String operator() default "";
}
