package com.wen.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.usercenter.model.domain.User;
import com.wen.usercenter.service.UserService;
import com.wen.usercenter.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
* @author Cwb
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-11-12 18:16:06
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 账号、密码以及确认密码的长度校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }
        if (userAccount.length() < 4) {
            return -1;
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            return -1;
        }
        // 账号不能包含特殊字符
        if (!userAccount.matches("^[0-9a-zA-Z]{4,}$")) {
            return -1;
        }
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }
        // 账号重复校验
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        long count = this.count(queryWrapper);
        if (count > 0) {
            return -1;
        }
        // 密码加密
        final String SALT = "wen";
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 存储数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean savResult = this.save(user);
        if (!savResult) {
            return -1;
        }
        return user.getId();
    }
}




