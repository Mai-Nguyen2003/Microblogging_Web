package oth.ics.wtp.taDa_backend.repositories;

import org.springframework.data.repository.CrudRepository;
import oth.ics.wtp.taDa_backend.entities.AppUser;

import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, String> {
    boolean existsByName(String name);
    Optional<AppUser> findByName(String name);
}
