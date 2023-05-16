package com.chunyang.demobackend.service;


import com.chunyang.demobackend.common.AuthConstant;
import com.chunyang.demobackend.common.UserContext;
import com.chunyang.demobackend.dao.UserMapper;
import com.chunyang.demobackend.dto.LoginDTO;
import com.chunyang.demobackend.dto.TokenDTO;
import com.chunyang.demobackend.dto.UserDTO;
import com.chunyang.demobackend.entity.UserEntity;
import com.chunyang.demobackend.exception.BusinessException;
import com.chunyang.demobackend.util.TokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class UserService implements AuthConstant {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FollowService followService;

    /**
     * create a new account
     *
     * @param userDTO
     * @return
     */
    @Transactional
    public UserDTO userRegister(UserDTO userDTO) {
        if (queryUserByUsername(userDTO.getName()) != null) {
            throw new BusinessException(USER_USERNAME_EXISTED);
        }

        checkEmail(userDTO.getEmail());
        checkPassword(userDTO.getPassword());

        addUser(userDTO);
        userDTO.setPassword("");

        return userDTO;
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new BusinessException(PASSWORD_CANNOT_BE_NULL);
        }
    }

    private void checkEmail(String email) {
        boolean emailIsValid = validateEmail(email);
        if (!emailIsValid) {
            throw new BusinessException(USER_EMAIL_ILLEGAL);
        }
    }

    private boolean validateEmail(String email) {
        if (email == null) {
            return false;
        }
        Pattern p = Pattern.compile(EMAIL_REGEX);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    private UserEntity addUser(UserDTO userDTO) {
        String pwd = userDTO.getPassword();
        String passwordMd5 = DigestUtils.md5DigestAsHex(pwd.getBytes());
        userDTO.setPassword(passwordMd5);

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDTO, userEntity);

        userMapper.insertUser(userEntity);
        return userEntity;
    }

    public UserEntity queryUserByUsername(String username) {
        return userMapper.getUserByName(username);
    }

    public UserEntity queryUserByUserId(int userId) {
        return userMapper.getUserById(userId);
    }

    /**
     * process login
     *
     * @param loginDTO
     * @return
     */
    public TokenDTO login(LoginDTO loginDTO) {
        String username = loginDTO.getName();
        String password = loginDTO.getPassword();

        UserEntity user = userMapper.getUserByName(username);

        if (!DigestUtils.md5DigestAsHex(password.getBytes())
                .equals(user.getPassword())) {
            throw new BusinessException("username or password is incorrect!");
        }

        return getTokenResponse(user);
    }

    private TokenDTO getTokenResponse(UserEntity user) {
        String token = TokenUtil.generateToken(user);

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(token);

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);

        tokenDTO.setUser(userDTO);

        return tokenDTO;
    }

    /**
     * get current user info
     *
     * @return
     */
    public UserDTO getCurrentUserInfo() {
        UserDTO userDTO = UserContext.getUser();
        UserEntity user = userMapper.getUserByName(userDTO.getName());

        int followingCnt = followService.getFollowingsCountByFollowerId(user.getId());
        int followerCnt = followService.getFollowersCountByFollowedId(user.getId());

        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .followingCnt(followingCnt)
                .followerCnt(followerCnt)
                .build();
    }

    public UserDTO getUserInfoByUserIdentity(String identity) {
        UserEntity user;
        user = userMapper.getUserByName(identity);

        if(Objects.isNull(user)){
            user = userMapper.getUserByEmail(identity);
        }

        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}