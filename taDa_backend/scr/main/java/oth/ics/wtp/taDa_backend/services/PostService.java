package oth.ics.wtp.taDa_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import oth.ics.wtp.taDa_backend.ClientErrors;
import oth.ics.wtp.taDa_backend.dtos.*;
import oth.ics.wtp.taDa_backend.entities.Post;
import oth.ics.wtp.taDa_backend.entities.AppUser;
import oth.ics.wtp.taDa_backend.repositories.PostRepository;
import oth.ics.wtp.taDa_backend.repositories.AppUserRepository;

import java.time.Instant;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class PostService {
    private final PostRepository postRepo;
    private final AppUserRepository userRepo;

    @Autowired
    public PostService(PostRepository postRepo, AppUserRepository userRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    public PostDto create(AppUser user, PostCreateDto createPost) {
        if (createPost.messageText() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Conent");
        }
        Post post = toEntity(createPost, user);
        postRepo.save(post);
        user.getPosts().add(post);
        userRepo.save(user);
        return toDto(post);
    }


    public List<PostDto> list(String userName) {
        return postRepo.findAllByUserName(userName).stream().map(PostService::toDto).toList();
    }
    public List<PostDto> listAllPosts() {
        return StreamSupport.stream(postRepo.findAll().spliterator(), false).map(PostService::toDto).toList();
    }

    public void delete(AppUser user, long postId) {
        Post p = postRepo.findByUserNameAndId(user.getUserName(), postId).orElseThrow(() -> ClientErrors.postNotFound(postId));
        postRepo.delete(p);
    }

    public PostDto get(String userName, long postId) {
        Post p = postRepo.findByUserNameAndId(userName, postId).orElseThrow(() -> ClientErrors.postNotFound(postId));
        return toDto(p);
    }

    static PostDto toDto(Post post) {
        AppUser user = post.getUser();
        AppUserBriefDto briefUserDto = new AppUserBriefDto(user.getUserName());
        List<CommentBriefDto> briefCommentDtos = post.getComments() == null ? null : post.getComments().stream().map(c -> new CommentBriefDto(c.getId(), c.getTextComment(), new AppUserBriefDto(c.getUser().getUserName()))).toList();
        List<LikeBriefDto> briefLikeDtos = post.getLikes() == null ? null : post.getLikes().stream().map(l -> new LikeBriefDto(l.getId(), new AppUserBriefDto(l.getUser().getUserName()))).toList();
        return new PostDto(post.getId(), post.getTextMessage(), post.getCreatedAt(),
                briefUserDto, briefCommentDtos, briefLikeDtos);
    }

    static Post toEntity(PostCreateDto createPost, AppUser user) {
        return new Post(createPost.messageText(), Instant.now(), user);
    }
}
