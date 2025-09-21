package com.singleton.boardproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentUpdateRequestDto {

    @NotBlank(message = "댓글 내용은 비어 있을 수 없습니다.")
    private String commentContent;
}
