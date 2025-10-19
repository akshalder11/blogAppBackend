package com.blogapp.blogappbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post_reactions", uniqueConstraints = {@UniqueConstraint(columnNames = {"userId", "postId"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId; // will later be @ManyToOne

    @Column(nullable = false)
    private Long postId; // will later be @ManyToOne

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReactionType reactionType;

    public enum ReactionType {
        LIKE, DISLIKE
    }
}
