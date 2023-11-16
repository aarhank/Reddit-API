package com.aarhankhan.redditapi.service;

import com.aarhankhan.redditapi.dtos.Post;
import com.aarhankhan.redditapi.dtos.SubReddit;
import com.aarhankhan.redditapi.requests.SubRedditRequest;
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
public class SubRedditServiceImpl implements SubRedditService{
    @Override
    public SubReddit getSubredditPosts(SubRedditRequest subRedditRequest) {
        Document webPage = null;
        try {
            webPage = Jsoup
                    .connect("https://old.reddit.com/r/"+ subRedditRequest.getName() +"/"+ subRedditRequest.getType() +"/")
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
        SubReddit subReddit = new SubReddit();
        subReddit.setPosts(posts);
        subReddit.setName(subRedditRequest.getName());
        return subReddit;
    }
}
