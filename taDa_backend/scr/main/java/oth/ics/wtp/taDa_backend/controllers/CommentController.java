package oth.ics.wtp.taDa_backend.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import oth.ics.wtp.taDa_backend.dtos.CommentCreateDto;
import oth.ics.wtp.taDa_backend.dtos.CommentDto;
import oth.ics.wtp.taDa_backend.entities.AppUser;
import oth.ics.wtp.taDa_backend.services.AuthService;
import oth.ics.wtp.taDa_backend.services.CommentService;

import java.util.List;

@SecurityRequirement(name = "basicAuth")
@RestController
public class CommentController {
    private final CommentService commentService;
    private final AuthService authService;

    @Autowired
    public CommentController(CommentService commentService, AuthService authService) {
        this.commentService = commentService;
        this.authService = authService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "posts/{postId}/comments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CommentDto createComment(HttpServletRequest request, @PathVariable("postId") Long postId, @RequestBody CommentCreateDto createComment) {
        AppUser user = authService.getAuthenticatedUser(request);
        return commentService.create(postId, createComment, user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "posts/{postId}/comments/{id}")
    public void deleteComment(HttpServletRequest request, @PathVariable("postId") Long postId, @PathVariable("id") Long id) {
        AppUser user = authService.getAuthenticatedUser(request);
        commentService.delete(postId, id, user);
    }

    @GetMapping(value = "posts/{postId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CommentDto> listComments(@PathVariable("postId") long postId) {
        return commentService.list(postId);
    }

    @GetMapping(value = "posts/{postId}/comments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommentDto getComment(@PathVariable("postId") Long postId, @PathVariable("id") Long id) {
        return commentService.get(postId, id);
    }

}
