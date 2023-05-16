package com.chunyang.demobackend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FollowDTO {

    private Integer id;

    private Integer followerId;

    private Integer followedId;

    private Date createTime;

    private Date updateTime;
}
