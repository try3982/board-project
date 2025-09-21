package com.singleton.boardproject.dto;

import com.singleton.boardproject.entity.Member;
import com.singleton.boardproject.entity.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostRequestDto {

    @NotBlank(message = "제목 입력은 필수 입력값입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력값입니다.")
    private String content;

    public Post toEntity(Member member) {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .author(member)
                .likeCount(0L)
                .viewCount(0L)
                .build();
    }

}