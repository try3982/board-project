package com.singleton.boardproject.controller;

import com.singleton.boardproject.dto.LikeResponseDto;
import com.singleton.boardproject.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<LikeResponseDto> like(
            @PathVariable Long postId,
            @RequestParam String email
    ) {
        return ResponseEntity.ok(likeService.likePost(postId, email));
    }

    @DeleteMapping
    public ResponseEntity<LikeResponseDto> unlike(
            @PathVariable Long postId,
            @RequestParam String email
    ) {
        return ResponseEntity.ok(likeService.unlikePost(postId, email));
    }

}
