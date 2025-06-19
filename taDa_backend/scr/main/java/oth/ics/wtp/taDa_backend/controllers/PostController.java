package oth.ics.wtp.taDa_backend.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import oth.ics.wtp.taDa_backend.dtos.AppUserDto;
import oth.ics.wtp.taDa_backend.dtos.PostCreateDto;
import oth.ics.wtp.taDa_backend.dtos.PostDto;
import oth.ics.wtp.taDa_backend.entities.AppUser;
import oth.ics.wtp.taDa_backend.services.AuthService;
import oth.ics.wtp.taDa_backend.services.PostService;

import java.util.List;

@SecurityRequirement(name = "basicAuth")
@RestController
public class PostController {
    private final PostService postService;
    private final AuthService authService;

    @Autowired
    public PostController(PostService postService, AuthService authService) {
        this.postService = postService;
        this.authService = authService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "posts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PostDto createPost(HttpServletRequest request, @RequestBody PostCreateDto createPost) {
        AppUser user = authService.getAuthenticatedUser(request);
        return postService.create(user, createPost);
    }

    @DeleteMapping(value = "posts/{id}")
    public void deletePost(HttpServletRequest request, @PathVariable("id") Long id) {
        AppUser user = authService.getAuthenticatedUser(request);
        postService.delete(user, id);
    }

    @GetMapping(value = "posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PostDto> viewOwnPosts(HttpServletRequest request) {
        AppUser user = authService.getAuthenticatedUser(request);
        return postService.list(user.getUserName());
    }

    @GetMapping(value = "allPosts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PostDto> listAllPosts() {
        return postService.listAllPosts();
    }

    @GetMapping(value = "posts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PostDto viewPost(HttpServletRequest request, @PathVariable("id") Long id) {
        AppUser user = authService.getAuthenticatedUser(request);
        return postService.get(user.getUserName(), id);
    }

    @GetMapping(value = "users/{userName}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PostDto> viewOthersPosts(@PathVariable("userName") String userName) {
        return postService.list(userName);
    }

    @GetMapping(value = "users/{userName}/posts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PostDto getPost(@PathVariable("userName") String userName, @PathVariable("id") Long id) {
        return postService.get(userName, id);
    }
}
