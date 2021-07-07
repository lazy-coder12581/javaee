package com.alibaba.controller;

import com.alibaba.bean.Result;
import com.alibaba.bean.User;
import com.alibaba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册
     * @param user 参数封装
     * @return Result
     */
    @PostMapping(value = "/regist")
    public Result regist(User user){
        return userService.regist(user);
    }

    /**
     * 登录
     * @param user 参数封装
     * @return Result
     */
    @PostMapping(value = "/login")
    public Result login(User user){
        return userService.login(user);
    }

    @GetMapping(value = "/noPassTeachers")
    public Result getAllNoPassTeacher() {return userService.getAllNoPassTeacher();}

    @GetMapping(value = "/passTeacher")
    public Result passTeacher(int id) {return userService.passTeacher(id);}

    @PostMapping(value = "/changeUser")
    public Result changeUser(User user) {return userService.changeUser(user);}

    @GetMapping(value = "/selectUser")
    public Result selectUser(String value) {return userService.selectUser(value);}

    @GetMapping(value = "/deleteUser")
    public Result deleteUser(int id) {return userService.deleteUser(id);}
}

