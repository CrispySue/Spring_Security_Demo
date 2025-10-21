package com.crispysue.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginVo implements Serializable {
    // id
    private Long id;
    // 用户名
    private String username;
    // jwt 令牌
    private String token;
}
