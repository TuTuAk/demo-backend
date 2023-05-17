package com.chunyang.demobackend.service;

import com.chunyang.demobackend.dao.UserMapper;
import com.chunyang.demobackend.dto.LoginDTO;
import com.chunyang.demobackend.dto.TokenDTO;
import com.chunyang.demobackend.dto.UserDTO;
import com.chunyang.demobackend.entity.UserEntity;
import com.chunyang.demobackend.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.DigestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private FollowService followService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegister() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("chunyang");
        userDTO.setEmail("123@example.com");
        userDTO.setPassword("password");

        when(userMapper.getUserByName(userDTO.getName())).thenReturn(null);
        when(userMapper.getUserByEmail(userDTO.getEmail())).thenReturn(null);
        doNothing().when(userMapper).insertUser(any(UserEntity.class));

        UserDTO result = userService.userRegister(userDTO);

        assertNotNull(result);
    }


    @Test
    public void chunyangRegisterFailureInvalidEmail() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("chunyang");
        userDTO.setEmail("invalidemail");
        userDTO.setPassword("password");

        assertThrows(BusinessException.class, () -> {
            userService.userRegister(userDTO);
        });
    }


    @Test
    public void testLogin() {
        String username = "chunyang";
        String password = "password";

        UserEntity user = new UserEntity();
        user.setName(username);
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setName(username);
        loginDTO.setPassword(password);

        when(userMapper.getUserByName(username)).thenReturn(user);

        TokenDTO result = userService.login(loginDTO);

        assertNotNull(result);
    }

    @Test
    public void testLoginFailureIncorrectPassword() {
        String username = "chunyang";
        String password = "password";

        UserEntity user = new UserEntity();
        user.setName(username);
        user.setPassword(DigestUtils.md5DigestAsHex("incorrectpassword".getBytes()));

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setName(username);
        loginDTO.setPassword(password);

        when(userMapper.getUserByName(username)).thenReturn(user);

        assertThrows(BusinessException.class, () -> {
            userService.login(loginDTO);
        });
    }

    @Test
    public void testGetCurrentUserInfo() {
        int userId = 1;
        String username = "testuser";
        String email = "test@example.com";
        int followingCnt = 10;
        int followerCnt = 20;

        UserDTO userDTO = new UserDTO();
        userDTO.setName(username);

        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setName(username);
        user.setEmail(email);

        when(userMapper.getUserByName(username)).thenReturn(user);
        when(followService.getFollowingsCountByFollowerId(userId)).thenReturn(followingCnt);
        when(followService.getFollowersCountByFollowedId(userId)).thenReturn(followerCnt);

        UserDTO result = userService.getCurrentUserInfo();

        assertNotNull(result);
    }

    @Test
    public void testGetUserInfoByName() {
        int userId = 1;
        String username = "testuser";
        String email = "test@example.com";

        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setName(username);
        user.setEmail(email);

        when(userMapper.getUserByName(username)).thenReturn(user);

        UserDTO result = userService.getUserInfoByUserIdentity(username);

        assertNotNull(result);
    }

}