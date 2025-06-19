package oth.ics.wtp.taDa_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oth.ics.wtp.taDa_backend.ClientErrors;
import oth.ics.wtp.taDa_backend.entities.AppUser;
import oth.ics.wtp.taDa_backend.entities.Follow;
import oth.ics.wtp.taDa_backend.repositories.AppUserRepository;
import oth.ics.wtp.taDa_backend.repositories.FollowRepository;

import java.util.Optional;

@Service
public class FollowService {
    private final FollowRepository followRepo;
    private final AppUserRepository userRepo;

    @Autowired

    public FollowService(FollowRepository followRepo, AppUserRepository userRepo) {
        this.followRepo = followRepo;
        this.userRepo = userRepo;
    }

    public void create(AppUser user, String followedUserName) {
        AppUser followedUser = userRepo.findByName(followedUserName).orElseThrow(() -> ClientErrors.userNotFound(followedUserName));
        if (!followRepo.existsByFollowerNameAndFollowedName(user.getUserName(), followedUserName) && !(followedUserName.equals(user.getUserName()))) {
            Follow f = new Follow(user, followedUser);
            user.getFollowings().add(f);
            followedUser.getFollowers().add(f);
            followRepo.save(f);
        } else {
            throw ClientErrors.invalidFollow(followedUserName);
        }
    }

    public boolean isFollowing(String followerName, String followedName) {
        if (!userRepo.existsByName(followerName)) {
            throw ClientErrors.userNotFound(followerName);
        }
        if (!userRepo.existsByName(followedName)) {
            throw ClientErrors.userNotFound(followedName);
        }
        return followRepo.existsByFollowerNameAndFollowedName(followerName, followedName);
    }



    public void delete(AppUser user, String followedUserName) {
        AppUser followedUser = userRepo.findByName(followedUserName).orElseThrow(() -> ClientErrors.userNotFound(followedUserName));
        Optional<Follow> f = followRepo.findByFollowerNameAndFollowedName(user.getUserName(), followedUserName);
        f.ifPresent(followRepo::delete);
    }

}
