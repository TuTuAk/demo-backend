package com.chunyang.demobackend.service;

import com.chunyang.demobackend.common.UserContext;
import com.chunyang.demobackend.dao.FollowMapper;
import com.chunyang.demobackend.dto.FollowDTO;
import com.chunyang.demobackend.dto.UserDTO;
import com.chunyang.demobackend.entity.FollowEntity;
import com.chunyang.demobackend.entity.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class FollowService {

    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private UserService userService;

    /**
     * get all followings for the given follower id
     *
     * @param followerId
     * @return
     */
    public List<UserDTO> getFollowingsByFollowerId(int followerId) {
        List<FollowEntity> follows = followMapper.getFollowingsByFollowerId(followerId);

        return follows
                .stream()
                .map(followEntity -> {
                    UserEntity userEntity =
                            userService.queryUserByUserId(followEntity.getFollowedId());
                    UserDTO userDTO = new UserDTO();
                    BeanUtils.copyProperties(userEntity, userDTO);
                    return userDTO;
                }).collect(Collectors.toList());
    }

    /**
     * get all followers for the given followed id
     *
     * @param followedId
     * @return
     */
    public List<UserDTO> getFollowersByFollowedId(int followedId) {
        List<FollowEntity> follows = followMapper.getFollowersByFollowedId(followedId);

        return follows
                .stream()
                .map(followEntity -> {
                    UserEntity userEntity =
                            userService.queryUserByUserId(followEntity.getFollowedId());
                    UserDTO userDTO = new UserDTO();
                    BeanUtils.copyProperties(userEntity, userDTO);
                    return userDTO;
                }).collect(Collectors.toList());
    }

    public int getFollowingsCountByFollowerId(int followerId) {
        return followMapper.getFollowingsCntByFollowerId(followerId);
    }

    public Integer getFollowersCountByFollowedId(int followedId) {
        return followMapper.getFollowersCntByFollowedId(followedId);
    }

    /**
     * create a new follow relationship
     * @param followRequest
     */
    @Transactional
    public void createFollow(FollowDTO followRequest) {
        FollowEntity entity = new FollowEntity();
        UserDTO user = UserContext.getUser();
        BeanUtils.copyProperties(followRequest, entity);
        entity.setFollowerId(user.getId());
        followMapper.insertFollow(entity);
    }


    /**
     * process unfollow
     *
     * @param followDTO
     */
    @Transactional
    public void removeFollow(FollowDTO followDTO) {
        UserDTO user = UserContext.getUser();
        followMapper.deleteFollow(user.getId(), followDTO.getFollowedId());
    }

}
