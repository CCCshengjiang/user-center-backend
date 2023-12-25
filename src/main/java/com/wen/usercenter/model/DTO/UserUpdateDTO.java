package com.wen.usercenter.model.DTO;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private String username;
    private String avatarUrl;
    private String email;
    private Integer gender;
    private String phone;
}
