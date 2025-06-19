package oth.ics.wtp.taDa_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oth.ics.wtp.taDa_backend.ClientErrors;
import oth.ics.wtp.taDa_backend.WeakCrypto;
import oth.ics.wtp.taDa_backend.dtos.*;
import oth.ics.wtp.taDa_backend.dtos.*;
import oth.ics.wtp.taDa_backend.entities.AppUser;
import oth.ics.wtp.taDa_backend.repositories.AppUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class AppUserService {
    private final AppUserRepository userRepo;

    @Autowired
    public AppUserService(AppUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public AppUserDto create(AppUserCreateDto createUser) {
        if (createUser.name() == null || createUser.name().isEmpty() ||
                createUser.password() == null || createUser.password().isEmpty()) {
            throw ClientErrors.invalidCredentials();
        }
        if (userRepo.existsByName(createUser.name())) {
            throw ClientErrors.userNameTaken(createUser.name());
        }
        AppUser user = toEntity(createUser);
        return toDto(userRepo.save(user));
    }

    public AppUserDto get(String name) {
        return userRepo.findByName(name).map(AppUserService::toDto)
                .orElseThrow(() -> ClientErrors.userNotFound(name));
    }

    public AppUserDto update(AppUser user, AppUserUpdateDto updateUser) {
        if (updateUser.fullName() == null || updateUser.fullName().isEmpty() ||
                updateUser.email() == null || updateUser.email().isEmpty() ||
                updateUser.biography() == null || updateUser.biography().isEmpty()) {
            throw ClientErrors.missingUpdateInfo();
        }
        user.setFullName(updateUser.fullName());
        user.setEmail(updateUser.email());
        user.setBiography(updateUser.biography());
        return toDto(userRepo.save(user));
    }

/*
    public void delete(String userName) {
        AppUser user = userRepo.findByUserName(userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        userRepo.delete(user);
    }*/

    public List<AppUserDto> list() {
        return StreamSupport.stream( userRepo.findAll().spliterator(), false).map(AppUserService::toDto).toList();
    }


    static AppUser toEntity(AppUserCreateDto createUser) {
        String hashedPassword = WeakCrypto.hashPassword(createUser.password());
        return new AppUser(createUser.name(), hashedPassword);

    }

    static AppUserDto toDto(AppUser user) {
        List<AppUserBriefDto> followers = user.getFollowers() == null ? new ArrayList<>() : user.getFollowers().stream().map(f -> new AppUserBriefDto(f.getFollower().getUserName())).toList();
        List<AppUserBriefDto> followings = user.getFollowings() == null ? new ArrayList<>() :user.getFollowings().stream().map(f -> new AppUserBriefDto(f.getFollowed().getUserName())).toList();
        return new AppUserDto(user.getUserName(), user.getFullName(), user.getEmail(), user.getBiography(),
                user.getPosts() == null ? null : user.getPosts().stream().map(p -> new PostBriefDto(p.getId(), p.getTextMessage())).toList(),
                followers,followings);
    }

}
