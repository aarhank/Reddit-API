package com.aarhankhan.redditapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String name;
    private List<Post> posts;
    private List<Comments> comments;
    private String commentKarma;
    private String postKarma;

}
