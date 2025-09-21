package com.singleton.boardproject.dto;


import com.singleton.boardproject.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private int likeCount;
    private int commentCount;
    private int viewCount;





}
