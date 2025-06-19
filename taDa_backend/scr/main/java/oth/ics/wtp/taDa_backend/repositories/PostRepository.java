package oth.ics.wtp.taDa_backend.repositories;

import org.springframework.data.repository.CrudRepository;
import oth.ics.wtp.taDa_backend.entities.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long> {
    @Override
    boolean existsById(Long id);
    Optional<Post> findByUserNameAndId(String username, Long id);
    Optional<Post> findById(Long id);
    List<Post>findAllByUserName(String userName);
}
