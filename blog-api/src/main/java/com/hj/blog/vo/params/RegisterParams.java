package com.hj.blog.vo.params;

import lombok.Data;

@Data
public class RegisterParams {
    private String account;
    private String password;
    private String nickname;
}
