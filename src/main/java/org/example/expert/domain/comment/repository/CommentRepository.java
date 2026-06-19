package org.example.expert.domain.comment.repository;

import org.example.expert.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // level 2-2 n+1 문제 해결 - join fetch 사용
    @Query("""
            SELECT c
            FROM Comment c
            JOIN FETCH c.user
            WHERE c.todo.id = :todoId
            """)
    List<Comment> findByTodoIdWithUser(@Param("todoId") Long todoId);
}
