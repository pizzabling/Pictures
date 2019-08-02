package ru.itpark.diplome.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itpark.diplome.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByActivationCode(String code);
}
