package com.singleton.boardproject.repository;

import com.singleton.boardproject.entity.Like;
import com.singleton.boardproject.entity.Member;
import com.singleton.boardproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByMemberAndPost(Member member, Post post);

    Optional<Like> findByMemberAndPost(Member member, Post post);

    long countByPost(Post post);
}
