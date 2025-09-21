package com.singleton.boardproject.repository;

import com.singleton.boardproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost_IdAndIsDeletedFalse(Long postId);

    Optional<Comment> findByIdAndIsDeletedFalse(Long id);
}
