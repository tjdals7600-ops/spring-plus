package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;

import java.util.Optional;


// JPA와 queryDSL을 같이쓰기위해 커스텀 레포지토리 패턴 사용
// queryDSL을 레포지토리 계층으로 분리
public interface TodoRepositoryCustom {
    Optional<Todo> findByIdWithUser(Long todoId);
}
