package com.crispysue.service;


import com.crispysue.pojo.dto.LoginDTO;
import com.crispysue.pojo.vo.UserLoginVo;

public interface LoginService {
    UserLoginVo login(LoginDTO loginDTO);
}
