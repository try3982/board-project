package com.singleton.boardproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

    @NotBlank(message = "댓글 내용은 필수 입력값입니다.")
    private String commentContent;

    @NotBlank(message = "작성자 이메일은 필수 입력값입니다.")
    private String email;

    private Long postId;
}
