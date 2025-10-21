package com.crispysue.filter;

import com.crispysue.mapper.UserMapper;
import com.crispysue.property.JWTProperties;
import com.crispysue.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * 自定义过虑器（验证 JWT）
 * 注入到Spring Security 过滤器链中
 */
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JWTProperties jwtProperties;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {
        // 访问路径
        String path = request.getRequestURI();
        if(path.startsWith("/login")){
            filterChain.doFilter(request,response);
            return;
        }
        // 获取标头的 jwt
        String token = request.getHeader(jwtProperties.getUserTokenName());
        try{
            // 获取 jwt 令牌中的信息
            Claims claims = JWTUtil.parseJWT(jwtProperties.getUserSecretKey(),token);
            Integer userId = Integer.valueOf(claims.get("userId").toString());
            String role = userMapper.getRoleByUserId(userId);
            // 手动生成认证过的 token 类，并装入 SecurityContext 中
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userId,null,List.of(new SimpleGrantedAuthority(role)));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // 执行原有操作
            filterChain.doFilter(request,response);
        }catch (Exception e){
            // 获取失败，返回 401
            response.setStatus(401);
            return;
        }
    }
}
