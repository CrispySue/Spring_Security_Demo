package com.crispysue.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO implements Serializable {
    // 用户名
    private String username;
    // 密码
    private String password;
}
