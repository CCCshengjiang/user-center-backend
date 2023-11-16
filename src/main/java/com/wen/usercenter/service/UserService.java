package com.wen.usercenter.service;

import com.wen.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author Cwb
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-11-12 18:16:06
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册校验
     * @param userAccount 用户输入账号
     * @param userPassword 用户输入密码
     * @param checkPassword 用户输入确认密码
     * @param idCode 编号
     * @return 返回id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String idCode);

    /**
     * 用户登录校验
     *
     * @param userAccount        用户输入账号
     * @param userPassword       用户输入密码
     * @param httpServletRequest 请求
     * @return 返回脱敏的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest httpServletRequest);

    /**
     * 用户信息脱敏
     * @param originUser 原用户信息
     * @return 脱敏的用户信息
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     *
     * @param request 请求
     * @return 返回一个int值
     */
    int userLogout(HttpServletRequest request);

}
