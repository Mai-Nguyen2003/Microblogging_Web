package oth.ics.wtp.taDa_backend.repositories;

import org.springframework.data.repository.CrudRepository;
import oth.ics.wtp.taDa_backend.entities.PostLike;

import java.util.Optional;

public interface LikeRepository extends CrudRepository<PostLike, Long> {
    boolean existsByPostIdAndUserName(Long postId, String userName);

    Optional<PostLike> findByPostIdAndUserName(Long postId, String userName);
}
