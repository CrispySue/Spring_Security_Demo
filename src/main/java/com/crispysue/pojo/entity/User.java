package com.crispysue.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * User 实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    // id
    private Integer id;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 权限
    private String role;
}
