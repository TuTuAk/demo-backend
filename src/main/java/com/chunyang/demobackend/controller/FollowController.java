package com.chunyang.demobackend.controller;

import com.chunyang.demobackend.dto.FollowDTO;
import com.chunyang.demobackend.dto.UserDTO;
import com.chunyang.demobackend.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FollowController {

    @Autowired
    private FollowService followService;

    /**
     * create a new follow relationship
     *
     * @param follow
     * @return
     */
    @PostMapping("/follow")
    public ResponseEntity<?> createFollow(@RequestBody FollowDTO follow) {
        followService.createFollow(follow);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * do unfollow operation
     *
     * @param followDTO
     * @return
     */
    @DeleteMapping("/unfollow")
    public ResponseEntity<?> follow(@RequestBody FollowDTO followDTO) {
        followService.removeFollow(followDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/follow/following/{followerId}")
    public ResponseEntity<List<UserDTO>> findFollowings(@PathVariable("followerId") int followerId) {
        List<UserDTO> follows = followService.getFollowingsByFollowerId(followerId);
        return ResponseEntity.ok(follows);
    }

    @GetMapping("/follow/follower/{followedId}")
    public ResponseEntity<List<UserDTO>> findFollowers(@PathVariable("followedId") int followedId) {
        List<UserDTO> follows = followService.getFollowersByFollowedId(followedId);
        return ResponseEntity.ok(follows);
    }

    @GetMapping("/follow/following/{followerId}/count")
    public ResponseEntity<Integer> countFollowings(@PathVariable("followerId") int followerId) {
        int count = followService.getFollowingsCountByFollowerId(followerId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/follow/follower/{followedId}/count")
    public ResponseEntity<Integer> countFollowers(@PathVariable("followedId") int followedId) {
        int count = followService.getFollowersCountByFollowedId(followedId);
        return ResponseEntity.ok(count);
    }
}
