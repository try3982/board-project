package com.singleton.boardproject.service;

import com.singleton.boardproject.dto.PostRequestDto;
import com.singleton.boardproject.dto.PostResponseDto;
import com.singleton.boardproject.dto.PostUpdateRequestDto;
import com.singleton.boardproject.entity.Member;
import com.singleton.boardproject.entity.Post;
import com.singleton.boardproject.repository.MemberRepository;
import com.singleton.boardproject.repository.PostRepository;
import com.singleton.exception.custom.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.singleton.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public PostResponseDto createPost(PostRequestDto request, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Post post = request.toEntity(member);
        Post saved = postRepository.save(post);

        return new PostResponseDto(
                saved.getId(),
                saved.getTitle(),
                saved.getContent(),
                saved.getLikeCount() == null ? 0 : saved.getLikeCount().intValue(),
                saved.getComments() == null ? 0 : saved.getComments().size(),
                saved.getViewCount() == null ? 0 : saved.getViewCount().intValue()
        );
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> getPostsByAuthorEmail(String email, Pageable pageable) {
        return postRepository.findAllByAuthor_Email(email, pageable)
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(this::toResponse);
    }

    private PostResponseDto toResponse(Post p) {
        return new PostResponseDto(
                p.getId(),
                p.getTitle(),
                p.getContent(),
                p.getLikeCount() == null ? 0 : p.getLikeCount().intValue(),
                p.getComments() == null ? 0 : p.getComments().size(),
                p.getViewCount() == null ? 0 : p.getViewCount().intValue()
        );
    }

    @Transactional
    public PostResponseDto updatePost(Long postId, String email, PostUpdateRequestDto request) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        if (!post.getAuthor().getId().equals(member.getId())) {
            throw new CustomException(AUTHORIZATION_FAILED);
        }

        post.update(request.getTitle(), request.getContent());

        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getLikeCount() == null ? 0 : post.getLikeCount().intValue(),
                post.getComments() == null ? 0 : post.getComments().size(),
                post.getViewCount() == null ? 0 : post.getViewCount().intValue()
        );
    }

    @Transactional
    public void deletePost(Long postId, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        if (!post.getAuthor().getId().equals(member.getId())) {
            throw new CustomException(AUTHORIZATION_FAILED);
        }

        postRepository.delete(post);
    }


}
