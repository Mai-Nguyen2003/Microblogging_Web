package oth.ics.wtp.taDa_backend.repositories;

import org.springframework.data.repository.CrudRepository;
import oth.ics.wtp.taDa_backend.entities.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long postId);
}
