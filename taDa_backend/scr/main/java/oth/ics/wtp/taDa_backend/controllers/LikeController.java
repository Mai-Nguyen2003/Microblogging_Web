package oth.ics.wtp.taDa_backend.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import oth.ics.wtp.taDa_backend.entities.AppUser;
import oth.ics.wtp.taDa_backend.services.AuthService;
import oth.ics.wtp.taDa_backend.services.LikeService;

@SecurityRequirement(name = "basicAuth")
@RestController
public class LikeController {
    private final AuthService authService;
    private final LikeService likeService;

    @Autowired
    public LikeController(AuthService authService, LikeService likeService) {
        this.authService = authService;
        this.likeService = likeService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "post/{postId}/like", produces = MediaType.APPLICATION_JSON_VALUE)
    public void setLike(HttpServletRequest request, @PathVariable("postId") long postId) {
        AppUser user = authService.getAuthenticatedUser(request);
        likeService.create(postId, user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "post/{postId}/like")
    public void deleteLike(HttpServletRequest request, @PathVariable("postId") long postId) {
        AppUser user = authService.getAuthenticatedUser(request);
        likeService.delete(postId, user);
    }


    @GetMapping(value = "post/{postId}/like", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean isLiked(HttpServletRequest request, @PathVariable("postId") long postId) {
        AppUser user = authService.getAuthenticatedUser(request);
        return likeService.isLiked(postId, user);
    }

}
