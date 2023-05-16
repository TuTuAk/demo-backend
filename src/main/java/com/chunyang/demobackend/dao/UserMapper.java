package com.chunyang.demobackend.dao;


import com.chunyang.demobackend.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    void insertUser(UserEntity user);

    void deleteUserById(int id);

    void updateUser(UserEntity user);

    UserEntity getUserById(int id);

    UserEntity getUserByEmail(String email);

    UserEntity getUserByName(String name);

    List<UserEntity> getAllUsers();

}
