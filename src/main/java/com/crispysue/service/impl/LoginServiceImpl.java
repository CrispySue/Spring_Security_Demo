package com.crispysue.service.impl;

import com.crispysue.mapper.UserMapper;
import com.crispysue.pojo.dto.LoginDTO;
import com.crispysue.pojo.entity.User;
import com.crispysue.pojo.vo.UserLoginVo;
import com.crispysue.property.JWTProperties;
import com.crispysue.service.LoginService;
import com.crispysue.token.CustomLoginAuthenticationToken;
import com.crispysue.utils.JWTUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JWTProperties jwtProperties;

    @Override
    public UserLoginVo login(LoginDTO loginDTO){
        // 获取用户信息
        User user =  userMapper.getUserByUsername(loginDTO.getUsername());
        if(user == null){
            // TODO: 异常处理，处理为友好响应
            throw new RuntimeException("用户不存在");
        }
        // 自定义 token 类，用于Spring Security 认证
        CustomLoginAuthenticationToken authenticationToken = new CustomLoginAuthenticationToken(user,loginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 认证通过
        UserLoginVo userLoginvo = new UserLoginVo();
        BeanUtils.copyProperties(user,userLoginvo);
        // jwt 负载信息
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",user.getId());
        // 生成 JWT 令牌
        String token = JWTUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims
        );
        userLoginvo.setToken(token);
        return userLoginvo;
    }
}
