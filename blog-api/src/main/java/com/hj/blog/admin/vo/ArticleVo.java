package com.hj.blog.admin.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hj.blog.admin.pojo.ArticleBody;
import com.hj.blog.admin.pojo.Category;
import com.hj.blog.admin.pojo.Tag;
import lombok.Data;

import java.util.List;

//此处创建ArticleVo的原因是因为从数据库拿出的全部records即数据不一定要全部给前端展示，因此需要vo对象选择性的传给前端需要的数据
@Data
public class ArticleVo {
    //@JsonSerialize(using = ToStringSerializer.class)
    private String id;
    private String title;
    private String summary;
    private Integer commentCounts;
    private Integer viewCounts;
    private Integer weight;
    private String crateDate;
    private String author = "adia";

    private List<TagVo> tags;

    private ArticleBodyVo body;
    private CategoryVo category;
}
