package com.wen.usercenter.model.domain.request;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 *
 * @author wen
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 5904429575465599378L;

    private String userAccount;
    private String userPassword;
}
