package hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hello.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
