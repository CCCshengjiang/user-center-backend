package com.wen.usercenter.common;

import lombok.Data;

/**
 * 错误码
 *
 * @author wen
 */
public enum ErrorCode {
    SUCCESS(20000, "success", ""),

    PARAMS_ERROR(40000, "请求参数错误", ""),
    PARAMS_NULL_ERROR(40001, "请求数据为空", ""),
    NOT_LOGIN(40100, "未登录", ""),
    NOT_AUTH(40101, "无权限", ""),
    SYSTEM_ERROR(50000, "系统内部异常", ""),
    INVALID_PASSWORD_ERROR(40102, "无效密码", "");
    private final int code;
    /**
     * 状态码信息
     */
    private final String message;
    /**
     * 状态码详情
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
