package com.chunyang.demobackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedDTO {

    private Integer id;

    private Integer userId;

    private String userName;

    private String content;

    private Date createTime;

    private Date updateTime;
}
