package oth.ics.wtp.taDa_backend.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import oth.ics.wtp.taDa_backend.entities.AppUser;
import oth.ics.wtp.taDa_backend.services.AuthService;
import oth.ics.wtp.taDa_backend.services.FollowService;

@SecurityRequirement(name = "basicAuth")
@RestController
public class FollowController {
    private final FollowService followService;
    private final AuthService authService;

    @Autowired
    public FollowController(FollowService followService, AuthService authService) {
        this.followService = followService;
        this.authService = authService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "users/{followingName}/follows", produces = MediaType.APPLICATION_JSON_VALUE)
    public void setFollow(HttpServletRequest request, @PathVariable("followingName") String followedUserName) {
        AppUser user = authService.getAuthenticatedUser(request);
        followService.create(user, followedUserName);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "users/{followingName}/follows")
    public void unFollow(HttpServletRequest request, @PathVariable("followingName") String followedUserName) {
        AppUser user = authService.getAuthenticatedUser(request);
        followService.delete(user, followedUserName);
    }

    @GetMapping(value = "users/{followingName}/follows", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean isFollowing(HttpServletRequest request, @PathVariable("followingName") String followedUserName) {
        AppUser user = authService.getAuthenticatedUser(request);
        return followService.isFollowing(user.getUserName(), followedUserName);
    }

    @GetMapping(value = "users/{followerName}/followed", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean isFollowed(HttpServletRequest request, @PathVariable("followerName") String followerUserName) {
        AppUser user = authService.getAuthenticatedUser(request);
        return followService.isFollowing(followerUserName,user.getUserName());
    }


}
