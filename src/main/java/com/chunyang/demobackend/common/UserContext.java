package com.chunyang.demobackend.common;


import com.chunyang.demobackend.dto.UserDTO;

public class UserContext {

    private static final ThreadLocal<UserDTO> USER_HOLDER = new ThreadLocal<>();

    public static UserDTO getUser() {
        return USER_HOLDER.get();
    }

    public static void setUser(UserDTO user) {
        USER_HOLDER.set(user);
    }
}
