package com.wen.usercenter.model.DTO;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Cwb
 */
@Data
public class PasswordUpdateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -5996345129538944393L;

    /**
     * 原密码
     */
    private String userPassword;

    /**
     * 新密码
     */
    private String newPassword;
}
