package com.danigu.blog;

import com.danigu.blog.dic.StationLocator;
import com.danigu.blog.post.Post;
import com.danigu.blog.post.PostService;

/**
 * @author dani
 */
public class ServerApplication {
    public static void main(String[] args) {
        StationLocator locator = new StationLocator();
        PostService postService = locator.getPostService();

        System.out.println("hello!");
        postService.create("first post", "wow");
        postService.create("second post", "such content");
        postService.create("third post", "very database");
        postService.getAll().stream().map(Post::toString).forEach(System.out::println);
    }
}
