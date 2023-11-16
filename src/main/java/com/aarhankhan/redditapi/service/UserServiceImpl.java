package com.aarhankhan.redditapi.service;

import com.aarhankhan.redditapi.dtos.Comments;
import com.aarhankhan.redditapi.dtos.Post;
import com.aarhankhan.redditapi.dtos.SubReddit;
import com.aarhankhan.redditapi.dtos.User;
import com.aarhankhan.redditapi.requests.UserRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    public User getUserInfo(User user, Document webPage){
        String postKarma = webPage.select("body > div.side > div:nth-child(1) > div > span.karma").not("span.karma.comment-karma").text();
        String commentKarma =  webPage.select("body > div.side > div:nth-child(1) > div > span.karma.comment-karma").text();
        user.setCommentKarma(commentKarma);
        user.setPostKarma(postKarma);
        return user;
    }
    @Override
    public User getUserPosts(UserRequest user) {
        Document webPage = null;
        System.out.println("https://old.reddit.com/user/"+ user.getName() + "/submitted/");
        try {
            webPage = Jsoup
                    .connect("https://old.reddit.com/user/"+ user.getName() + "/submitted/")
                    .get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements listItems = webPage.select("#siteTable > div");
        System.out.println(listItems.size());
        List<Post> posts = new ArrayList<Post>();
        for (Element listItem : listItems) {
            String postTitle = listItem.select("div.entry.unvoted > div.top-matter > p.title > a").text();
            String postUrl = "https://old.reddit.com" + listItem.attr("data-permalink");
            String previewImage =  listItem.select(" a > img").attr("src");
            String imageUrl = listItems.select("div > a > img").attr("src");
            String isPromoted = listItem.select("div.entry.unvoted > div.top-matter > ul > li:nth-child(1) > span").attr("class");
            String totalComments = listItem.select("div.entry.unvoted > div.top-matter > ul > li.first > a").text();
            String originalPoster = listItem.select("div.entry.unvoted > div.top-matter > p.tagline > a").text();

            if(!postTitle.isEmpty() && !Objects.equals(isPromoted, "promoted-tag")) {
                Post post = new Post(postTitle,postUrl, originalPoster, null,totalComments, previewImage, imageUrl, "",null,null);
                posts.add(post);
            }
        }
        User user1 = new User();
        user1.setPosts(posts);
        user1.setName(user.getName());
        return getUserInfo(user1,webPage);
    }

    @Override
    public User getUserComments(UserRequest user) {
        Document webPage = null;
        System.out.println("https://old.reddit.com/user/"+ user.getName() + "/comments/");
        try {
            webPage = Jsoup
                    .connect("https://old.reddit.com/user/"+ user.getName() + "/comments/")
                    .get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements listItems = webPage.select("#siteTable > div");
        System.out.println(listItems.size());
        List<Comments> comments = new ArrayList<Comments>();
        for (Element listItem : listItems) {
            if( !listItem.attr("id").isEmpty()) {
                Comments comment = new Comments();
                String commentText = listItem.select("#" + listItem.attr("id") + " > div.entry.unvoted > form > div > div > p").text();
                String commentAuthor = listItem.select("#" + listItem.attr("id") + " > div.entry.unvoted > p > a").text();
                comment.setText(commentText);
                comment.setAuthor(commentAuthor);
                comments.add(comment);
            }
        }
        User user1 = new User();
        user1.setComments(comments);
        user1.setName(user.getName());
        return getUserInfo(user1,webPage);
    }
}
