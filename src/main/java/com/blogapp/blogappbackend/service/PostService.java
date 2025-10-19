package com.blogapp.blogappbackend.service;

import com.blogapp.blogappbackend.entity.Post;

import java.util.List;

public interface PostService {
    Post createPost(Post post);
    Post getPostById(Long postId);
    List<Post> getAllPosts();
    Post updatePost(Post post, Long postId);
    void deletePost(Long postId);
    List<Post> searchPostsByTitle(String keyword);
    List<Post> searchPostsByContent(String keyword);
}
