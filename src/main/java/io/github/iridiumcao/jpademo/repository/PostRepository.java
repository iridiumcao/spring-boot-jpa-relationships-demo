package io.github.iridiumcao.jpademo.repository;

import io.github.iridiumcao.jpademo.entity.Post;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<@NonNull Post, @NonNull Long> {
}
