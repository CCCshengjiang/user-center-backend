package com.wen.usercenter.service;

import com.wen.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * @return 返回id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

}
