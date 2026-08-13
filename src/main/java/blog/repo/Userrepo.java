package blog.repo;

import blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Userrepo extends JpaRepository<User, Integer> {
    List<User> findByNameContaining(String name);
    Optional<User> findByEmail(String email);

}