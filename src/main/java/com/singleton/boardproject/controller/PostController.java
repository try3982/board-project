package com.singleton.boardproject.controller;

import com.singleton.boardproject.dto.PostRequestDto;
import com.singleton.boardproject.dto.PostResponseDto;
import com.singleton.boardproject.dto.PostUpdateRequestDto;
import com.singleton.boardproject.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;



    @PostMapping
    public ResponseEntity<PostResponseDto> create(
            @Valid @RequestBody PostRequestDto request,
            @RequestParam String email) {
        return ResponseEntity.ok(postService.createPost(request, email));
    }

    @GetMapping("/by-author")
    public ResponseEntity<Page<PostResponseDto>> byAuthorEmail(
            @RequestParam String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(
                postService.getPostsByAuthorEmail(email, PageRequest.of(page, size))
        );
    }

    @GetMapping()
    public ResponseEntity<Page<PostResponseDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction
    ) {
        Sort sort = "ASC".equalsIgnoreCase(direction)
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(postService.getAllPosts(pageable));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long postId,
            @RequestParam String email,
            @Valid @RequestBody PostUpdateRequestDto request
    ) {
        return ResponseEntity.ok(postService.updatePost(postId, email, request));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(
            @PathVariable Long postId,
            @RequestParam String email
    ) {
        postService.deletePost(postId, email);
        return ResponseEntity.ok(Map.of("message", "게시글이 삭제되었습니다."));
    }


}
