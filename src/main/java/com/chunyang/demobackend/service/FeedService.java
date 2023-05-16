package com.chunyang.demobackend.service;

import com.chunyang.demobackend.common.UserContext;
import com.chunyang.demobackend.dao.FeedMapper;
import com.chunyang.demobackend.dto.FeedDTO;
import com.chunyang.demobackend.dto.UserDTO;
import com.chunyang.demobackend.entity.FeedEntity;
import com.chunyang.demobackend.entity.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedService {

    @Autowired
    private FeedMapper feedMapper;

    @Autowired
    private UserService userService;

    /**
     * create a new feed
     *
     * @param feedDTO
     * @return
     */
    @Transactional
    public FeedDTO createFeed(FeedDTO feedDTO) {
        UserDTO useDto = UserContext.getUser();
        FeedEntity feed = convertToFeed(feedDTO);
        feed.setUserId(useDto.getId());
        UserEntity user = userService.queryUserByUserId(feed.getUserId());

        feed.setUserName(user.getName());
        feed.setCreateTime(new Date());
        feed.setUpdateTime(new Date());
        feedMapper.insertFeed(feed);

        return convertToFeedDTO(feed);
    }

    /**
     * get all feeds for the given userId
     *
     * @param userId
     * @return
     */
    public List<FeedDTO> getFeedsByUserId(Integer userId) {
        List<FeedEntity> feeds = feedMapper.getFeedsByUserId(userId);
        return feeds.stream()
                .map(this::convertToFeedDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public FeedDTO updateFeed(int id, FeedDTO feedDTO) {
        FeedEntity existingFeed = feedMapper.getFeedById(id);
        existingFeed.setUpdateTime(new Date());

        FeedEntity updatedFeed = convertToFeed(feedDTO);
        updatedFeed.setId(existingFeed.getId());
        feedMapper.updateFeed(updatedFeed);

        return convertToFeedDTO(updatedFeed);
    }

    /**
     * delete a feed
     *
     * @param id
     */
    @Transactional
    public void deleteFeed(Integer id) {
        feedMapper.deleteFeedById(id);
    }

    private FeedEntity convertToFeed(FeedDTO feedDTO) {
        FeedEntity entity = new FeedEntity();
        BeanUtils.copyProperties(feedDTO, entity);
        return entity;
    }

    /**
     * TODO  use page
     *
     * @return
     */
    public List<FeedDTO> getAllFeeds() {
        List<FeedEntity> feeds = feedMapper.getAllFeeds();

        return feeds.stream()
                .map(this::convertToFeedDTO)
                .collect(Collectors.toList());
    }

    private FeedDTO convertToFeedDTO(FeedEntity feed) {
        return new FeedDTO(
                feed.getId(),
                feed.getUserId(),
                feed.getUserName(),
                feed.getContent(),
                feed.getCreateTime(),
                feed.getUpdateTime()
        );
    }
}
