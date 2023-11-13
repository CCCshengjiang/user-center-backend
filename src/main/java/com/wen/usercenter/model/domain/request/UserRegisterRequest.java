package com.wen.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author wen
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = -4157977884839010887L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
