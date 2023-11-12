package com.wen.usercenter.service;
import java.util.Date;

import com.wen.usercenter.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Resource
    public UserService userService;

    @Test
    public void testSelect() {
        User user = new User();
        user.setId(0L);
        user.setUsername("wen");
        user.setAvatarUrl("asflhaslkgjbab");
        user.setGender(0);
        user.setUserAccount("13245668");
        user.setUserPassword("123456");
        user.setPhone("123456789123");
        user.setEmail("31654@awg54.asd");
        user.setUserStatus(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDelete(0);

        System.out.println(userService.save(user));
        System.out.println(user.getId());

        Assertions.assertTrue(true);
    }

    @Test
    void userRegister() {
        String userAccount = "wenbo";
        String userPassword = "12345678";
        String checkPassword = "12345678";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
    }
}