package com.chunyang.demobackend.service;

import com.chunyang.demobackend.common.UserContext;
import com.chunyang.demobackend.dao.FollowMapper;
import com.chunyang.demobackend.dto.FollowDTO;
import com.chunyang.demobackend.dto.UserDTO;
import com.chunyang.demobackend.entity.FollowEntity;
import com.chunyang.demobackend.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class FollowServiceTest {

    @Mock
    private FollowMapper followMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private FollowService followService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetFollowingsByFollowerId() {
        int followerId = 1;
        List<FollowEntity> follows = new ArrayList<>();
        FollowEntity followEntity = new FollowEntity();
        followEntity.setFollowedId(2);
        follows.add(followEntity);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(2);
        userEntity.setName("chunyang");
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userEntity, userDTO);

        when(followMapper.getFollowingsByFollowerId(followerId)).thenReturn(follows);
        when(userService.queryUserByUserId(anyInt())).thenReturn(userEntity);

        List<UserDTO> result = followService.getFollowingsByFollowerId(followerId);

        assertEquals(1, result.size());
        assertEquals("chunyang", result.get(0).getName());
        verify(followMapper, times(1)).getFollowingsByFollowerId(followerId);
        verify(userService, times(1)).queryUserByUserId(2);
    }

    @Test
    public void testCreateFollow() {
        FollowDTO followDTO = new FollowDTO();
        followDTO.setFollowedId(2);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        UserContext.setUser(userDTO);

        FollowEntity followEntity = new FollowEntity();
        BeanUtils.copyProperties(followDTO, followEntity);
        followEntity.setFollowerId(userDTO.getId());

        when(followMapper.getFollowerByFollowedIdAndFollowerId(2, 1)).thenReturn(null);

        followService.createFollow(followDTO);

        verify(followMapper, times(1)).insertFollow(followEntity);
    }

    @Test
    public void testRemoveFollow() {
        FollowDTO followDTO = new FollowDTO();
        followDTO.setFollowedId(2);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        UserContext.setUser(userDTO);

        followService.removeFollow(followDTO);

        verify(followMapper, times(1)).deleteFollow(userDTO.getId(), 2);
    }
}
