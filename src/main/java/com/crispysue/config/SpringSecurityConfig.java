package com.crispysue.config;

import com.crispysue.filter.JWTAuthenticationFilter;
import com.crispysue.provider.CustomLoginAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfig{

    @Autowired
    private CustomLoginAuthenticationProvider customLoginAuthenticationProvider;

    // 密码加密器
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //关闭 csrf 保护
                .csrf(AbstractHttpConfigurer::disable)
                // 关闭 Session 存储登录状态
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 关闭 SpringSecurity 自带的登录拦截
                .formLogin(AbstractHttpConfigurer::disable)
                // 拦截器配置
                .authorizeHttpRequests(auth -> auth
                        // 无需登录及权限
                        .requestMatchers(
                                "/login"
                        ).permitAll()
                        // 需 USER 权限
                        .requestMatchers(
                                "/HelloWorld"
                        ).hasAnyRole("USER")
                        // 其余页面均需登录
                        .anyRequest().authenticated()
                )
                // 注入自定义认证器
                .authenticationProvider(customLoginAuthenticationProvider)
                // 注入自定义过虑器
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
