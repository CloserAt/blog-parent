package com.hj.blog.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hj.blog.admin.dao.dos.Archives;
import com.hj.blog.admin.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    /*
    文章归档
     */
    List<Archives> listArchives();

    IPage<Article> listArticle(Page<Article> page,Long categoryId, Long tagId, String year, String month);
}
