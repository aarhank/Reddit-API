package com.aarhankhan.redditapi.service;

import com.aarhankhan.redditapi.dtos.Comments;
import com.aarhankhan.redditapi.dtos.Post;
import com.aarhankhan.redditapi.dtos.SubReddit;
import com.aarhankhan.redditapi.requests.PostRequest;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PostServiceImpl implements PostService {

    public List<Comments> getNestedComments(Elements listItems){
        List<Comments> comments = new ArrayList<Comments>();
        for (Element listItem : listItems) {
            if( !listItem.attr("id").isEmpty()) {
                Comments comment = new Comments();
                String commentText = listItem.select("#" + listItem.attr("id") + " > div.entry.unvoted > form > div > div > p").text();
                String commentAuthor = listItem.select("#" + listItem.attr("id") + " > div.entry.unvoted > p > a").text();
                Elements childrens = listItem.select("div.child > div > div");
                List<Comments> nestedComments = new ArrayList<Comments>();
                if (!childrens.isEmpty()) {
                    nestedComments = getNestedComments(childrens);
                }
                comment.setText(commentText);
                comment.setAuthor(commentAuthor);
                comment.setChildren(nestedComments);
                comments.add(comment);
            }
        }
        return comments;
    }
    @Override
    public Post getPostComments(PostRequest postRequest) {
        Document webPage = null;
        try {
            webPage = Jsoup
                    .connect(postRequest.getUrl())
                    .get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String id = StringUtils.substringBetween(postRequest.getUrl(), "comments/", "/");
        Elements listItems = webPage.select("#siteTable_t3_"+ id + " > div");

        List<Comments> comments = getNestedComments(listItems);
        Post post = new Post();
        post.setComments(comments);
        return post;
    }

    @Override
    public Post getPostData(PostRequest postRequest) {
        Document webPage = null;
        try {
            webPage = Jsoup
                    .connect(postRequest.getUrl())
                    .get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Elements postData = webPage.select("#siteTable");
        String postTitle = postData.select("div.entry.unvoted > div.top-matter > p.title > a").text();
        String postFlair = postData.select("div.entry.unvoted > div.top-matter > p.title > span.linkflairlabel").text();
        String postImgUrl = postData.select(" div.entry.unvoted > div.expando > div > div > a > img").attr("src");
        String postUpvotes = postData.select(" div.midcol.unvoted > div.score.unvoted").text();
        String postOriginalPoster = postData.select(" div.entry.unvoted > div.top-matter > p.tagline > a").text();
        String postTime = postData.select("div.entry.unvoted > div.top-matter > p.tagline > time").text();
        String postTotalComments = postData.select(" div.entry.unvoted > div.top-matter > ul > li.first > a").text();
//        try(InputStream in = new URL(postImgUrl).openStream()){
//            Files.copy(in, Paths.get("/Users/aarhankhan/Documents/stuff/image.jpg"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        return new Post(postTitle,postRequest.getUrl(),postOriginalPoster,null,postTotalComments,null,postImgUrl,postUpvotes,postFlair,postTime);

    }
}
