package com.blogapp.blogappbackend.controllers;

import com.blogapp.blogappbackend.entity.PostReaction;
import com.blogapp.blogappbackend.service.PostReactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/postReactions")
@RequiredArgsConstructor
public class PostReactionController {

    private final PostReactionService postReactionService;

    // REACT
    @PostMapping("/reactPost")
    public ResponseEntity<?> reactToPost (@RequestBody Map<String, String> payload) {
        try {
            Long postId = Long.parseLong(payload.get("postId"));
            Long userId = Long.parseLong(payload.get("userId"));
            String reactionType = payload.get("reactionType");

            postReactionService.reactToPost(postId, userId, reactionType);
            return ResponseEntity.ok(Map.of("message", "React added to post successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // REMOVE REACT
    @DeleteMapping("/removeReactPost")
    @Transactional
    public ResponseEntity<?> removeReaction (@RequestBody Map<String, String> payload) {
        try {
            Long postId = Long.parseLong(payload.get("postId"));
            Long userId = Long.parseLong(payload.get("userId"));
            postReactionService.removeReaction(postId, userId);
            return ResponseEntity.ok(Map.of("message", "React removed successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // GET ALL REACT FOR A POST
    @GetMapping("/getAllReactForPost")
    public ResponseEntity<?> getAllReaction (@RequestBody Map<String, String> payload) {
        try {
            Long postId = Long.parseLong(payload.get("postId"));
            List<PostReaction> reactions = postReactionService.getReactionByPost(postId);
            return ResponseEntity.ok(Map.of(
                    "postId", postId,
                    "reactionCount", reactions.size(),
                    "reactions", reactions
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
