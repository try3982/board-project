package com.singleton.boardproject.dto;

import com.singleton.boardproject.entity.Like;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeResponseDto {
    private Long likeId;
    private Long postId;
    private String memberEmail;
    private String message;

    public static LikeResponseDto fromEntity(Like like, String message) {
        return new LikeResponseDto(
                like.getId(),
                like.getPost().getId(),
                like.getMember().getEmail(),
                message
        );
    }
}
