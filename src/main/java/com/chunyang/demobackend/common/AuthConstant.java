package com.chunyang.demobackend.common;


public interface AuthConstant {
    String DEFAULT_TOKEN_NAME = "Authorization";

    String CLAIM_KEY_USERNAME = "sub";

    String CLAIM_KEY_USER_ID = "userId";

    String CLAIM_KEY_USER_EMAIL = "userEmail";

    String CLAIM_KEY_CREATE_TIME = "createTime";

    String TOKEN_SECRET = "eHh4eGJiYmNjY2RkZGVlZWZmZmRkZA==";

    int TOKEN_EXPIRATION = 86400;

    String JWT_AUTHENTICATION_FILTER = "jwtAuthenticationFilter";

    String EMAIL_REGEX = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

    String AUTH_TOKEN_TIMEOUT = "token has been expired";

    String AUTH_USER_UNAUTHORIZED = " you have no permission to operate";

    String AUTH_USERNAME_CANNOT_BE_NULL = "username can't be null";

    String AUTH_USER_NOT_EXIST = "the user doesn't exist";

    String USER_EMAIL_ILLEGAL = "email is not valid";

    String USER_USERNAME_EXISTED = "the username has been used";

    String PASSWORD_CANNOT_BE_NULL = "password can't be null";

}
