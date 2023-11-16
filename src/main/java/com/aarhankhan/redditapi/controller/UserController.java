package com.aarhankhan.redditapi.controller;

import com.aarhankhan.redditapi.dtos.Post;
import com.aarhankhan.redditapi.dtos.User;
import com.aarhankhan.redditapi.requests.UserRequest;
import com.aarhankhan.redditapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/posts")
    public User getUserPosts(@RequestBody UserRequest user){
        return userService.getUserPosts(user);
    }

    @PostMapping("/comments/")
    public User getUserComments(@RequestBody UserRequest user){
        return userService.getUserComments(user);
    }
}
