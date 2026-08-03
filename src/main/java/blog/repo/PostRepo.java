package blog.repo;

import blog.entity.Category;
import blog.entity.Post;
import blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    List<Post> findByTittleContaining(String tittle);


}