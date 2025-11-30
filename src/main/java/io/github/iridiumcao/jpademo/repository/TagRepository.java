package io.github.iridiumcao.jpademo.repository;

import io.github.iridiumcao.jpademo.entity.Tag;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<@NonNull Tag, @NonNull Long> {
}
