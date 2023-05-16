package com.chunyang.demobackend.controller;

import com.chunyang.demobackend.dto.FeedDTO;
import com.chunyang.demobackend.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feed")
public class FeedController {

    @Autowired
    private FeedService feedService;

    /**
     * create a new feed
     *
     * @param feedDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<FeedDTO> createFeed(@RequestBody FeedDTO feedDTO) {
        FeedDTO feed = feedService.createFeed(feedDTO);
        return new ResponseEntity<>(feed, HttpStatus.CREATED);
    }

    /**
     * update a feed
     *
     * @param id
     * @param feed
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<FeedDTO> updateFeed(@PathVariable int id, @RequestBody FeedDTO feed) {
        FeedDTO updatedFeed = feedService.updateFeed(id, feed);
        return new ResponseEntity<>(updatedFeed, HttpStatus.OK);
    }

    /**
     * delete one feed
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeed(@PathVariable int id) {
        feedService.deleteFeed(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * get all feeds for a user
     *
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<FeedDTO>> getUserFeeds(@PathVariable int userId) {
        List<FeedDTO> feeds = feedService.getFeedsByUserId(userId);
        return new ResponseEntity<>(feeds, HttpStatus.OK);
    }

    /**
     * get all feeds
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<FeedDTO>> getAllFeeds() {
        List<FeedDTO> feeds = feedService.getAllFeeds();
        return new ResponseEntity<>(feeds, HttpStatus.OK);
    }
}
