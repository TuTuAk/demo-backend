package com.chunyang.demobackend.dao;


import com.chunyang.demobackend.entity.FeedEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface FeedMapper {

    void insertFeed(FeedEntity feed);

    void deleteFeedById(int id);

    void updateFeed(FeedEntity feed);

    FeedEntity getFeedById(int id);

    List<FeedEntity> getFeedsByUserId(int userId);

    List<FeedEntity> getAllFeeds();


}