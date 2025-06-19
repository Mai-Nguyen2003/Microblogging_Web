package oth.ics.wtp.taDa_backend.services;

import org.springdoc.webmvc.ui.SwaggerResourceResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oth.ics.wtp.taDa_backend.ClientErrors;
import oth.ics.wtp.taDa_backend.dtos.AppUserBriefDto;
import oth.ics.wtp.taDa_backend.dtos.CommentCreateDto;
import oth.ics.wtp.taDa_backend.dtos.CommentDto;
import oth.ics.wtp.taDa_backend.dtos.PostBriefDto;
import oth.ics.wtp.taDa_backend.entities.Comment;
import oth.ics.wtp.taDa_backend.entities.Post;
import oth.ics.wtp.taDa_backend.entities.AppUser;
import oth.ics.wtp.taDa_backend.repositories.CommentRepository;
import oth.ics.wtp.taDa_backend.repositories.PostRepository;
import oth.ics.wtp.taDa_backend.repositories.AppUserRepository;

import java.time.Instant;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepo;
    private final PostRepository postRepo;

    @Autowired
    public CommentService(CommentRepository commentRepo, PostRepository postRepo, AppUserRepository userRepo, SwaggerResourceResolver swaggerResourceResolver) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
    }

    public CommentDto create(long postId, CommentCreateDto createComment, AppUser user) {
        Post post = postRepo.findById(postId).orElseThrow(() ->ClientErrors.postNotFound(postId));
        Comment c = toEntity(createComment, user,post);
        post.getComments().add(c);
        postRepo.save(post);
        return toDto(commentRepo.save(c));
    }

    public void delete(long postId, long id,AppUser user) {
        Comment comment = findComment(postId, id);
        if(user.getUserName().equals(comment.getUser().getUserName())){
        commentRepo.delete(comment);
        }else {
            throw ClientErrors.userDeleteOtherComment();
        }
    }

    public CommentDto get(long postId, long id) {
        Comment comment = findComment(postId, id);
        return toDto(comment);
    }

    private Comment findComment(long postId, long id) {
        Comment comment = commentRepo.findById(id).orElseThrow(() -> ClientErrors.commentNotFound(id));
        if (comment.getPost().getId() != postId) {
            throw ClientErrors.postNotFound(postId);
        }
        return comment;
    }

    public List<CommentDto> list(long postId) {
        return commentRepo.findAllByPostId(postId).stream().map(CommentService::toDto).toList();
    }


    static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getTextComment(), comment.getCreatedAt(),
                new AppUserBriefDto(comment.getUser().getUserName()),
                new PostBriefDto(comment.getPost().getId(),comment.getPost().getTextMessage()));
    }

    static Comment toEntity(CommentCreateDto createComment,AppUser user, Post post) {
        return new Comment(createComment.commentText(), Instant.now(), user, post);
    }


}
