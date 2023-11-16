package com.aarhankhan.redditapi.service;

import com.aarhankhan.redditapi.dtos.Post;
import com.aarhankhan.redditapi.dtos.User;
import com.aarhankhan.redditapi.requests.UserRequest;

import java.util.List;

public interface UserService {
    User getUserPosts(UserRequest user);

    User getUserComments(UserRequest user);
}
