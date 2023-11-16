package com.aarhankhan.redditapi.service;

import com.aarhankhan.redditapi.dtos.SubReddit;
import com.aarhankhan.redditapi.requests.SubRedditRequest;
import org.springframework.stereotype.Service;

public interface SubRedditService {
    SubReddit getSubredditPosts(SubRedditRequest subRedditRequest);
}
