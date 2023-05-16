package com.chunyang.demobackend.dao;

import com.chunyang.demobackend.entity.FollowEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;


@Mapper
public interface FollowMapper {

    void insertFollow(FollowEntity follow);

    void deleteFollowById(int id);

    void updateFollow(FollowEntity follow);

    FollowEntity getFollowById(int id);

    List<FollowEntity> getAllFollows();

    @Select(value = "select count(*) from follow where follower_id = #{followerId}")
    int getFollowingsCntByFollowerId(@Param("followerId") int followerId);

    @Select(value = "select count(*) from follow where followed_id =#{followedId}")
    int getFollowersCntByFollowedId(@Param("followedId") int followedId);

    @Select(value = "select * from follow where followed_id = #{followedId}")
    List<FollowEntity> getFollowersByFollowedId(@Param("followedId") int followedId);

    @Select(value = "select * from follow where follower_id = #{followerId}")
    List<FollowEntity> getFollowingsByFollowerId(@Param("followerId") int followerId);

    @Select(value = "delete from follow where follower_id = #{followerId} and followed_id = #{followedId}")
    void deleteFollow(@Param("followerId") int followerId, @Param("followedId") int followedId);
}
