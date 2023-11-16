package com.aarhankhan.redditapi.controller;

import com.aarhankhan.redditapi.dtos.Post;
import com.aarhankhan.redditapi.requests.PostRequest;
import com.aarhankhan.redditapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@CrossOrigin(origins = "*")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/comments")
    public Post getPostComments(@RequestBody PostRequest postRequest){
        return postService.getPostComments(postRequest);
    }

    @PostMapping("/info")
    public Post getPostData(@RequestBody PostRequest postRequest){
        return postService.getPostData(postRequest);
    }
}
