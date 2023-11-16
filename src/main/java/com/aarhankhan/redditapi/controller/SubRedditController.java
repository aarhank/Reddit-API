package com.aarhankhan.redditapi.controller;

import com.aarhankhan.redditapi.dtos.SubReddit;
import com.aarhankhan.redditapi.requests.SubRedditRequest;
import com.aarhankhan.redditapi.service.SubRedditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subreddit")
@CrossOrigin(origins = "*")
public class SubRedditController {

    @Autowired
    private SubRedditService subRedditService;

    @PostMapping("")
    public SubReddit getSubredditPosts(@RequestBody SubRedditRequest subRedditRequest) {
        return subRedditService.getSubredditPosts(subRedditRequest);
    }
}
