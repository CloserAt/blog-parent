package com.hj.blog.admin.vo.params;

import lombok.Data;

@Data
public class PageParams {
    private Integer currentPage;

    private Integer pageSize;

    private String queryString;
}
