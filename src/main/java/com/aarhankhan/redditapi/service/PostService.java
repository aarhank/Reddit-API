package com.aarhankhan.redditapi.service;

import com.aarhankhan.redditapi.dtos.Post;
import com.aarhankhan.redditapi.requests.PostRequest;

public interface PostService {
    Post getPostComments(PostRequest postRequest);

    Post getPostData(PostRequest postRequest);
}
