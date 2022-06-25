package com.hj.blog.vo.params;

import lombok.Data;
//post请求的参数
//此处使用vo层处理的原因是因为get请求不到控制层，需要封装一下
@Data
public class PageParams {
    private int page = 1;//默认值1
    private int pageSize = 10;//默认值10
}
