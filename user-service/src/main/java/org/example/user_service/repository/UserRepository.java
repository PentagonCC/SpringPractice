package org.example.user_service.repository;

import org.example.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    void delete(User user);

    User save(User user);

    Optional<User> findById(Long id);
}
