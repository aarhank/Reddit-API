package com.aarhankhan.redditapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubReddit {
    private String name;
    private String desc;
    private Long totalUsers;
    private List<Post> posts;
}
