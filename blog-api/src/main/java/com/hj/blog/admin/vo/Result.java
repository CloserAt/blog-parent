package com.hj.blog.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    private boolean success;
    private int code;
    private String msg;
    private Object data;

    //此处开发两个静态方法来便于构造Result
    public static Result success(Object SuccessData) {
        return new Result(true,200,"success",SuccessData);
    }
    public static Result fail(int code, String msg) {
        return new Result(false,code,msg,null);
    }
}
