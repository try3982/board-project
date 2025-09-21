package com.singleton.boardproject.service;

import com.singleton.boardproject.dto.LikeResponseDto;
import com.singleton.boardproject.entity.Like;
import com.singleton.boardproject.entity.Member;
import com.singleton.boardproject.entity.Post;
import com.singleton.boardproject.repository.LikeRepository;
import com.singleton.boardproject.repository.MemberRepository;
import com.singleton.boardproject.repository.PostRepository;
import com.singleton.exception.custom.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.singleton.exception.ErrorCode.*; // USER_NOT_FOUND, POST_NOT_FOUND 등

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public LikeResponseDto likePost(Long postId, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        if (likeRepository.existsByMemberAndPost(member, post)) {
            return new LikeResponseDto(null, post.getId(), member.getEmail(), "이미 좋아요 상태입니다.");
        }

        Like like = likeRepository.save(Like.builder().member(member).post(post).build());
        post.increaseLikeCount();
        return LikeResponseDto.fromEntity(like, "좋아요 완료");
    }


    @Transactional
    public LikeResponseDto unlikePost(Long postId, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        return likeRepository.findByMemberAndPost(member, post)
                .map(like -> {
                    likeRepository.delete(like);
                    post.decreaseLikeCount();
                    return LikeResponseDto.fromEntity(like, "좋아요 취소 완료");
                })
                .orElse(new LikeResponseDto(null, post.getId(), member.getEmail(), "좋아요 상태가 아닙니다."));
    }

}
