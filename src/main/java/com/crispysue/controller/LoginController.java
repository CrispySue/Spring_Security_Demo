package com.crispysue.controller;

import com.crispysue.pojo.dto.LoginDTO;
import com.crispysue.pojo.vo.UserLoginVo;
import com.crispysue.result.Result;
import com.crispysue.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录接口
     * @param loginDTO
     * @return
     */
    @RequestMapping("/login")
    Result login(LoginDTO loginDTO) {
       UserLoginVo userLoginVo = loginService.login(loginDTO);
       return Result.success(userLoginVo);
    }
}
