package com.crispysue.controller;

import com.crispysue.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/HelloWorld")
    public Result<String> helloworld(){
        return Result.success("Hello World");
    }
}
