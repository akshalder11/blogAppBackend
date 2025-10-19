package com.blogapp.blogappbackend.service;

import com.blogapp.blogappbackend.entity.PostReaction;

import java.util.List;

public interface PostReactionService {
    PostReaction reactToPost(Long postId, Long userId, String reactionType);
    void removeReaction(Long postId, Long userId);
    List<PostReaction> getReactionByPost(Long postId);
}
