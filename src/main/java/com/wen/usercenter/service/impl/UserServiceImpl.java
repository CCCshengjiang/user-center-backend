package com.wen.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectById;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.usercenter.common.ErrorCode;
import com.wen.usercenter.exception.BusinessException;
import com.wen.usercenter.model.DTO.PasswordUpdateDTO;
import com.wen.usercenter.model.domain.User;
import com.wen.usercenter.service.UserService;
import com.wen.usercenter.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import static com.wen.usercenter.constant.UserConstant.USER_LOGIN_STATUS;

/**
* @author Cwb
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-11-12 18:16:06
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;

    /**
     * 盐值：混淆密码
     */
    private static final String SALT = "wen";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String idCode) {
        // 账号、密码以及确认密码的长度校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR, "数据为空");
        }
        if (idCode.length() > 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编号过长");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号太短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码太短");
        }
        // 账号不能包含特殊字符
        if (!userAccount.matches("^[0-9a-zA-Z]{4,}$")) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号有特殊字符");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入密码不一致");
        }
        // 账号重复校验
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }
        // 编号重复校验
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id_code", idCode);
        count = this.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编号重复");
        }
        // 密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 存储数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setIdCode(idCode);
        // 后端设置默认用户名就是账号（前端没传递）
        user.setUsername(userAccount);
        user.setAvatarUrl("https://pic1.zhimg.com/80/v2-c8586e136574ebcbdc8ac5464cb36ff4_720w.webp");
        boolean savResult = this.save(user);
        if (!savResult) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "存储错误");
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest httpServletRequest) {
        // 账号、密码的长度校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "存储错误");
        }
        if (userAccount.length() < 4 || userPassword.length() < 8) {
            return null;
        }
        // 账号不能包含特殊字符
        if (!userAccount.matches("^[0-9a-zA-Z]{4,}$")) {
            return null;
        }
        // 密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 账号重复校验
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        queryWrapper.eq("user_password", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword!");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号或密码错误");
        }
        // 用户信息脱敏
        User safetyUser = getSafetyUser(user);
        // 记录用户的登录态
        httpServletRequest.getSession().setAttribute(USER_LOGIN_STATUS, safetyUser);
        // 返回用户信息
        return safetyUser;
    }

    @Override
    public User getSafetyUser(User originUser) {
        if (originUser == null) {
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setIdCode(originUser.getIdCode());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setUpdateTime(originUser.getUpdateTime());
        return safetyUser;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATUS);
        return 1;
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "未登录");
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "未登录");
        }
        return currentUser;
    }

    @Override
    public QueryWrapper<User> searchUsers(User userQuery) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (userQuery != null) {
            if (userQuery.getId() != null) {
                queryWrapper.eq("id", userQuery.getId());
            }
            if (userQuery.getUserRole() != null) {
                queryWrapper.eq("user_role", userQuery.getUserRole());
            }
            if (userQuery.getGender() != null) {
                queryWrapper.eq("gender", userQuery.getGender());
            }
            if (userQuery.getUserStatus() != null) {
                queryWrapper.eq("user_status", userQuery.getUserStatus());
            }
            if (StringUtils.isNotBlank(userQuery.getUsername())) {
                queryWrapper.like("username", userQuery.getUsername());
            }
            if (StringUtils.isNotBlank(userQuery.getUserAccount())) {
                queryWrapper.like("user_account", userQuery.getUserAccount());
            }
            if (StringUtils.isNotBlank(userQuery.getEmail())) {
                queryWrapper.like("email", userQuery.getEmail());
            }
            if (StringUtils.isNotBlank(userQuery.getIdCode())) {
                queryWrapper.eq("id_code", userQuery.getIdCode());
            }
            if (StringUtils.isNotBlank(userQuery.getPhone())) {
                queryWrapper.like("phone", userQuery.getPhone());
            }
        }
        return queryWrapper;
    }

    @Override
    public boolean updateUserPassword(PasswordUpdateDTO passwordUpdateDTO, HttpServletRequest request) {
        if (passwordUpdateDTO == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "前端没有密码传递进来");
        }
        User loginUser = getLoginUser(request);
        Long userId = loginUser.getId();
        if (userId == null || userId < 0) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR, "不存在该用户");
        }
        String encryptedOldPassword = DigestUtils.md5DigestAsHex((SALT + passwordUpdateDTO.getUserPassword()).getBytes());
        if (!loginUser.getUserPassword().equals(encryptedOldPassword)) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD_ERROR, "用户原本密码输入错误");
        }

        User user = new User();
        BeanUtils.copyProperties(passwordUpdateDTO, user);
        user.setId(loginUser.getId());

        // 使用 MD5 加密新密码
        String encryptedNewPassword = DigestUtils.md5DigestAsHex((SALT + passwordUpdateDTO.getNewPassword()).getBytes());
        user.setUserPassword(encryptedNewPassword);
        if (encryptedNewPassword.equals(passwordUpdateDTO.getUserPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "修改密码不能相同");
        }
        return updateById(user);
    }
}




