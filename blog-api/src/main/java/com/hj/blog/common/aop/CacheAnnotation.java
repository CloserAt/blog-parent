package com.hj.blog.common.aop;

import java.lang.annotation.*;

//aop方式实现统一缓存处理，无需加在每个接口上
//统一缓存处理的原因是因为内存的访问速度远远大于硬盘的访问速度
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheAnnotation {

    long expire() default 1 * 60 * 1000;//过期时间，缓存不能常驻于内存之中

    String name() default "";//自定义缓存前缀标识
}
