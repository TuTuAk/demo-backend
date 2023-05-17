package com.chunyang.demobackend.service;

import com.chunyang.demobackend.common.UserContext;
import com.chunyang.demobackend.dao.FeedMapper;
import com.chunyang.demobackend.dto.FeedDTO;
import com.chunyang.demobackend.dto.UserDTO;
import com.chunyang.demobackend.entity.FeedEntity;
import com.chunyang.demobackend.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class FeedServiceTest {

    @Mock
    private FeedMapper feedMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private FeedService feedService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateFeed() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        UserContext.setUser(userDTO);

        FeedDTO feedDTO = new FeedDTO();
        feedDTO.setContent("Test feed");

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setName("chunyang");

        FeedEntity feedEntity = new FeedEntity();
        BeanUtils.copyProperties(feedDTO, feedEntity);
        feedEntity.setUserId(userDTO.getId());
        feedEntity.setUserName(userEntity.getName());
        feedEntity.setCreateTime(new Date());
        feedEntity.setUpdateTime(new Date());

        when(userService.queryUserByUserId(userDTO.getId())).thenReturn(userEntity);
        doNothing().when(feedMapper).insertFeed(feedEntity);

        FeedDTO result = feedService.createFeed(feedDTO);

        assertNotNull(result);
    }

    @Test
    public void testGetFeedsByUserId() {
        int userId = 1;

        List<FeedEntity> feeds = new ArrayList<>();
        FeedEntity feedEntity = new FeedEntity();
        feedEntity.setId(1);
        feedEntity.setUserId(userId);
        feeds.add(feedEntity);

        when(feedMapper.getFeedsByUserId(userId)).thenReturn(feeds);

        List<FeedDTO> result = feedService.getFeedsByUserId(userId);

        assertNotNull(result);
    }

    @Test
    public void testUpdateFeed() {
        int id = 1;
        FeedDTO feedDTO = new FeedDTO();
        feedDTO.setContent("Updated feed");

        FeedEntity existingFeed = new FeedEntity();
        existingFeed.setId(id);
        existingFeed.setUpdateTime(new Date());

        FeedEntity updatedFeed = new FeedEntity();
        updatedFeed.setId(id);
        updatedFeed.setContent(feedDTO.getContent());
        updatedFeed.setUpdateTime(new Date());

        when(feedMapper.getFeedById(id)).thenReturn(existingFeed);
        doNothing().when(feedMapper).updateFeed(updatedFeed);

        FeedDTO result = feedService.updateFeed(id, feedDTO);

        assertNotNull(result);
    }

    @Test
    public void testDeleteFeed() {
        Integer id = 1;

        feedService.deleteFeed(id);

        verify(feedMapper, times(1)).deleteFeedById(id);
    }

    @Test
    public void testGetAllFeeds() {
        List<FeedEntity> feeds = new ArrayList<>();
        FeedEntity feedEntity = new FeedEntity();
        feedEntity.setId(1);
        feeds.add(feedEntity);

        when(feedMapper.getAllFeeds()).thenReturn(feeds);

        List<FeedDTO> result = feedService.getAllFeeds();

        assertNotNull(result);
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
