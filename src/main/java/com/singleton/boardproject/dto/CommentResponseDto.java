package com.singleton.boardproject.dto;

import com.singleton.boardproject.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String commentContent;
    private String authorEmail;
    private Long postId;

    public static CommentResponseDto fromEntity(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getCommentContent(),
                comment.getMember().getEmail(),
                comment.getPost().getId()
        );
    }


}
