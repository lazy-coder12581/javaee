package com.alibaba.mapper;

import com.alibaba.bean.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * mapper的具体表达式
 */
@Mapper //标记mapper文件位置，否则在Application.class启动类上配置mapper包扫描
@Repository
public interface UserMapper {

    /**
     * 查询用户名是否存在，若存在，不允许注册
     * 注解@Param(value) 若value与可变参数相同，注解可省略
     * 注解@Results  列名和字段名相同，注解可省略
     * @param username
     * @return
     */
    @Select(value = "select u.username,u.password from user u where u.username=#{username} and u.status = 1")
    @Results
            ({@Result(property = "username",column = "username"),
              @Result(property = "password",column = "password")})
    User findUserByName(@Param("username") String username);

    /**
     * 查询电话是否存在，若存在，不允许注册
     * 注解@Param(value) 若value与可变参数相同，注解可省略
     * 注解@Results  列名和字段名相同，注解可省略
     * @param phone
     * @return
     */
    @Select(value = "select * from user u where u.phone=#{phone} and u.status = 1")
    User findUserByPhone(@Param("phone") String phone);

    /**
     * 查询电话是否存在，若存在，不允许注册
     * 注解@Param(value) 若value与可变参数相同，注解可省略
     * 注解@Results  列名和字段名相同，注解可省略
     * @param id
     * @return
     */
    @Select(value = "select * from user u where u.id=#{id}")
    User findUserById(@Param("id") int id);

    /**
     * 注册  插入一条user记录
     * @param user
     * @return
     */
    @Insert("insert into user values(#{id},#{username},#{password},#{phone},#{identity},#{status})")
    //加入该注解可以保存对象后，查看对象插入id
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void regist(User user);

    /**
     * 登录
     * @param user
     * @return
     */
    @Select("select u.id from user u where u.username = #{username} and password = #{password}")
    Long login(User user);
    /**
     * 查询未授权教师
     * @return
     */
    @Select("select * from user  where identity = 1 and status = 0")
    List<User> allTeacherNoPass();

    /**
     * 更新用户
     * @param user
     * @return
     */
    @Update("update user set username = #{username}, password = #{password}, identity = #{identity}, status = #{status} where id = #{id}")
    void changeUser(User user);
}
