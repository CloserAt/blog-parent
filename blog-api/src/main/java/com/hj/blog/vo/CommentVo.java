package com.hj.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hj.blog.dao.pojo.Comment;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
public class CommentVo {
    //分布式id比较长，传到前端会有精度丢失，必须转换为String类型 进行传输就不会有问题了
    //传回前端时把id转成String就不会出现精度丢失问题
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private UserVo author;
    private String content;
    private List<CommentVo> childrens;
    private String createDate;
    private Integer level;
    private UserVo toUser;
}
