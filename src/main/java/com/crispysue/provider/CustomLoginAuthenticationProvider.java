package com.crispysue.provider;

import com.crispysue.mapper.UserMapper;
import com.crispysue.pojo.entity.User;
import com.crispysue.token.CustomLoginAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.Provider;
import java.util.List;

/**
 * 自定义认证类
 * 在调用 authenticationManager.authenticate 时自动调用容器中注册的 Provider
 * support 方法用于匹配认证的 token 类
 */
@Component
public class CustomLoginAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 1、验证密码
        User user = (User) authentication.getPrincipal();
        String encodedPassword = passwordEncoder.encode(authentication.getCredentials().toString());
        if(!user.getPassword().equals(encodedPassword)){
            throw new RuntimeException("密码错误");
        }
        // 清空密码，避免暴露
        user.setPassword(null);
        // 认证通过
        return new CustomLoginAuthenticationToken(
                user,
                List.of(new SimpleGrantedAuthority(user.getRole()))
        );
    }


    // 调用 authenticate 方法时用于匹配
    @Override
    public boolean supports(Class<?> authentication) {
        return CustomLoginAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
