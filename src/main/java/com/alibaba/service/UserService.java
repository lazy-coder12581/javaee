package com.alibaba.service;

import com.alibaba.bean.Result;
import com.alibaba.bean.User;
import com.alibaba.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserService {

    @Autowired
    private UserMapper userMapper;
    /**
     * 注册
     * @param user 参数封装
     * @return Result
     */
    public Result regist(User user) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            User existUser = userMapper.findUserByName(user.getUsername());
            User existUser_phone = userMapper.findUserByPhone(user.getPhone());
            if(existUser != null){
                //如果用户名已存在
                result.setMsg("用户名已存在");
            } else if(existUser_phone != null) {
                result.setMsg("手机号码重复");
            }else if(user.getIdentity() == 2) {
                result.setMsg("禁止注册管理员");
            }else {
                if(user.getIdentity() == 1)
                    user.setStatus(0);
                else
                    user.setStatus(1);
                userMapper.regist(user);
                //System.out.println(user.getId());
                result.setMsg("注册成功");
                result.setSuccess(true);
                result.setDetail(user);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 登录
     * @param user 用户名和密码
     * @return Result
     */
    public Result login(User user) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            Long userId= userMapper.login(user);
            if(userId == null){
                result.setMsg("用户名或密码错误");
            }else{
                result.setMsg("登录成功");
                result.setSuccess(true);
                user.setId(userId);
                result.setDetail(user);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result getAllNoPassTeacher() {
        Result result = new Result();
        try {
            List<User> users = userMapper.allTeacherNoPass();
            result.setMsg("查询成功");
            result.setSuccess(true);
            result.setDetail(users);
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result passTeacher(int id) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            User user = userMapper.findUserById(id);
            if(user == null) {
                result.setMsg("id不存在");
            }else if(user.getIdentity() != 1) {
                result.setMsg("用户不为教师");
            }else if(user.getStatus() == 1){
                result.setMsg("教师已被授权");
            }else {
                user.setStatus(1);
                userMapper.changeUser(user);
                result.setMsg("授权成功");
                result.setSuccess(true);
            }
        }catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result changeUser(User user) {
        Result result = new Result();
        try {
            userMapper.changeUser(user);
            result.setMsg("更改成功");
            result.setSuccess(true);
            result.setDetail("");
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result selectUser(String value) {
        Result result = new Result();
        result.setDetail(null);
        List<User> users = new LinkedList<>();
        try {
            users.add(userMapper.findUserByName(value));
            users.add(userMapper.findUserByPhone(value));
            result.setMsg("查询成功");
            result.setDetail(users);
        }catch (Exception e){
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result deleteUser(int id) {
        Result result = new Result();
        result.setDetail(null);
        try {
            User user = userMapper.findUserById(id);
            if(user == null) {
                result.setSuccess(false);
                result.setMsg("id不存在");
            }
            user.setStatus(0);
            userMapper.changeUser(user);
            result.setMsg("删除成功");
            result.setSuccess(true);
        }catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
