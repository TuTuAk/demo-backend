package com.chunyang.demobackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FollowDTO {

    private Integer id;

    private Integer followerId;

    private Integer followedId;

    private Date createTime;

    private Date updateTime;
}
