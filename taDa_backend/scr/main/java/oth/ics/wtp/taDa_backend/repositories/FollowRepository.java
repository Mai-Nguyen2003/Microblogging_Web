package oth.ics.wtp.taDa_backend.repositories;

import org.springframework.data.repository.CrudRepository;
import oth.ics.wtp.taDa_backend.entities.Follow;

import java.util.Optional;

public interface FollowRepository extends CrudRepository<Follow, String> {
    boolean existsByFollowerNameAndFollowedName(String followerName, String followEDName);

    Optional<Follow> findByFollowerNameAndFollowedName(String followerName, String followedName);
}
