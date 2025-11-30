package io.github.iridiumcao.jpademo.repository;

import io.github.iridiumcao.jpademo.entity.User;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<@NonNull User, @NonNull Long> {
}
