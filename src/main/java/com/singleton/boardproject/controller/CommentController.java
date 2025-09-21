package com.singleton.boardproject.controller;

import com.singleton.boardproject.dto.CommentRequestDto;
import com.singleton.boardproject.dto.CommentResponseDto;
import com.singleton.boardproject.dto.CommentUpdateRequestDto;
import com.singleton.boardproject.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @Valid @RequestBody CommentRequestDto request
    ) {
        return ResponseEntity.ok(commentService.createComment(request));
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostㅋ(postId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestParam String email,
            @Valid @RequestBody CommentUpdateRequestDto request
    ) {
        return ResponseEntity.ok(commentService.updateComment(commentId, email, request));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long commentId,
            @RequestParam String email
    ) {
        commentService.deleteComment(commentId, email);
        return ResponseEntity.ok(Map.of("message", "댓글이 삭제되었습니다."));
    }
}
