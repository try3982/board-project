package com.singleton.boardproject.service;

import com.singleton.boardproject.dto.CommentRequestDto;
import com.singleton.boardproject.dto.CommentResponseDto;
import com.singleton.boardproject.dto.CommentUpdateRequestDto;
import com.singleton.boardproject.entity.Comment;
import com.singleton.boardproject.entity.Member;
import com.singleton.boardproject.entity.Post;
import com.singleton.boardproject.repository.CommentRepository;
import com.singleton.boardproject.repository.MemberRepository;
import com.singleton.boardproject.repository.PostRepository;
import com.singleton.exception.ErrorCode;
import com.singleton.exception.custom.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.singleton.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        Comment comment = new Comment(
                null,
                request.getCommentContent(),
                post,
                member,
                false
        );

        Comment save = commentRepository.save(comment);
        return CommentResponseDto.fromEntity(save);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByPost(Long postId) {
        List<Comment> comments = commentRepository.findByPost_IdAndIsDeletedFalse(postId);

        return comments.stream()
                .map(CommentResponseDto::fromEntity)
                .toList();
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, String email, CommentUpdateRequestDto request) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Comment comment = commentRepository.findByIdAndIsDeletedFalse(commentId)
                .orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));

        if (!comment.getMember().getId().equals(member.getId())) {
            throw new CustomException(AUTHORIZATION_FAILED);
        }

        comment.updateContent(request.getCommentContent());
        return CommentResponseDto.fromEntity(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, String email) {
        Member requester = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));


        Comment comment = commentRepository.findByIdAndIsDeletedFalse(commentId)
                .orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));


        boolean isCommentAuthor = comment.getMember().getId().equals(requester.getId());
        boolean isPostAuthor = comment.getPost().getAuthor().getId().equals(requester.getId());

        if (!(isCommentAuthor || isPostAuthor)) {
            throw new CustomException(AUTHORIZATION_FAILED);
        }

        comment.softDelete();
    }
}
