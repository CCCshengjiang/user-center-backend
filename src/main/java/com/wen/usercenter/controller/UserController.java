package com.wen.usercenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wen.usercenter.common.BaseResponse;
import com.wen.usercenter.common.ErrorCode;
import com.wen.usercenter.exception.BusinessException;
import com.wen.usercenter.model.DTO.UserSearchDTO;
import com.wen.usercenter.model.DTO.UserUpdateDTO;
import com.wen.usercenter.model.domain.User;
import com.wen.usercenter.model.domain.request.UserLoginRequest;
import com.wen.usercenter.model.domain.request.UserRegisterRequest;
import com.wen.usercenter.service.UserService;
import com.wen.usercenter.utils.ResultUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.wen.usercenter.common.ErrorCode.PARAMS_NULL_ERROR;
import static com.wen.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.wen.usercenter.constant.UserConstant.USER_LOGIN_STATUS;


/**
 *用户接口
 *
 * @author wen
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    public UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> UserRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if (userRegisterRequest == null) {
            throw new BusinessException(PARAMS_NULL_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String idCode = userRegisterRequest.getIdCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, idCode)) {
            return ResultUtil.error(PARAMS_NULL_ERROR);
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword, idCode);
        return ResultUtil.success(result);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        User curUser = (User) request.getSession().getAttribute(USER_LOGIN_STATUS);
        if (curUser == null) {
            return ResultUtil.error(PARAMS_NULL_ERROR);
        }
        long userId = curUser.getId();
        User result = userService.getById(userId);
        return ResultUtil.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> UserLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest){
        if (userLoginRequest == null) {
            return ResultUtil.error(PARAMS_NULL_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return ResultUtil.error(PARAMS_NULL_ERROR);
        }
        User result = userService.userLogin(userAccount, userPassword, httpServletRequest);
        return ResultUtil.success(result);
    }


    @PostMapping("/logout")
    public BaseResponse<Integer> UserLogout(HttpServletRequest request){
        if (request == null) {
            return ResultUtil.error(PARAMS_NULL_ERROR);
        }

        int result = userService.userLogout(request);
        return ResultUtil.success(result);
    }

    /**
     * 查询用户
     *
     * @param userSearchDTO 用户字段信息
     * @param request 请求
     * @return 返回查到的用户和业务码
     */
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(UserSearchDTO userSearchDTO, HttpServletRequest request) {
        //判断是否是管理员
        if (!isAdmin(request)) {
            return ResultUtil.error(ErrorCode.NOT_AUTH);
        }
        User userQuery = new User();
        if (userSearchDTO != null) {
            BeanUtils.copyProperties(userSearchDTO, userQuery);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (userSearchDTO != null) {
            if (userSearchDTO.getId() != null) {
                queryWrapper.eq("id", userSearchDTO.getId());
            }
            if (userSearchDTO.getUserRole() != null) {
                queryWrapper.eq("user_role", userSearchDTO.getUserRole());
            }
            if (userSearchDTO.getGender() != null) {
                queryWrapper.eq("gender", userSearchDTO.getGender());
            }
            if (userSearchDTO.getUserStatus() != null) {
                queryWrapper.eq("user_status", userSearchDTO.getUserStatus());
            }
            if (StringUtils.isNotBlank(userSearchDTO.getUsername())) {
                queryWrapper.like("username", userSearchDTO.getUsername());
            }
            if (StringUtils.isNotBlank(userSearchDTO.getUserAccount())) {
                queryWrapper.like("user_account", userSearchDTO.getUserAccount());
            }
            if (StringUtils.isNotBlank(userSearchDTO.getEmail())) {
                queryWrapper.like("email", userSearchDTO.getEmail());
            }
            if (StringUtils.isNotBlank(userSearchDTO.getIdCode())) {
                queryWrapper.eq("id_code", userSearchDTO.getIdCode());
            }
            if (StringUtils.isNotBlank(userSearchDTO.getPhone())) {
                queryWrapper.like("phone", userSearchDTO.getPhone());
            }
        }
        // 添加其他字段的判断逻辑
        List<User> userList = userService.list(queryWrapper);
        List<User> result = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtil.success(result);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            return ResultUtil.error(PARAMS_NULL_ERROR);
        }
        if (id <= 0) {
            return ResultUtil.error(PARAMS_NULL_ERROR);
        }
        boolean result = userService.removeById(id);
        return ResultUtil.success(result);
    }

    /**
     * 修改用户信息
     *
     * @param request
     * @return
     */
    @GetMapping("/updateUser")
    public BaseResponse<User> updateUser(@RequestBody UserUpdateDTO userUpdateDTO, HttpServletRequest request) {
        if (userUpdateDTO == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        User user = new User();
        BeanUtils.copyProperties(userUpdateDTO, user);
        user.setId(loginUser.getId());
        boolean result = userService.updateById(user);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtil.success(loginUser);
    }

    /**
     * 判断是否为管理员
     *
     * @param request 前端请求
     * @return 返回是否为管理员
     */
    private boolean isAdmin(HttpServletRequest request) {
        // 判断是否为管理员
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATUS);
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }
}
