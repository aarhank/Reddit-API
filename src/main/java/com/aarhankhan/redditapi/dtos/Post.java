package com.aarhankhan.redditapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private String title;
    private String postUrl;
    private String originalPoster;
    private List<Comments> comments;
    private String totalComments;
    private String previewImgUrl;
    private String imgUrl;
    private String upvotes;
    private String flair;
    private String postedTime;

}
