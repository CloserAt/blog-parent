package com.hj.blog.vo;

import com.hj.blog.dao.pojo.Tag;
import lombok.Data;

import java.util.List;

//此处创建ArticleVo的原因是因为从数据库拿出的全部records即数据不一定要全部给前端展示，因此需要vo对象选择性的传给前端需要的数据
@Data
public class ArticleVo {
    private Long id;
    private String title;
    private String summary;
    private int commentCounts;
    private int viewCounts;
    private int weight;
    private String crateDate;
    private String author = "adia";

    private List<TagVo> tags;
}
