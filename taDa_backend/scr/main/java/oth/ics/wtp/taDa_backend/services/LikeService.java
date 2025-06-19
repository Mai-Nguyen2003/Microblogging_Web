package oth.ics.wtp.taDa_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import oth.ics.wtp.taDa_backend.ClientErrors;
import oth.ics.wtp.taDa_backend.entities.AppUser;
import oth.ics.wtp.taDa_backend.entities.PostLike;
import oth.ics.wtp.taDa_backend.entities.Post;
import oth.ics.wtp.taDa_backend.repositories.LikeRepository;
import oth.ics.wtp.taDa_backend.repositories.PostRepository;


import java.util.Optional;


@Service
public class LikeService {
    private final LikeRepository likeRepo;
    private final PostRepository postRepo;

    @Autowired

    public LikeService(LikeRepository likeRepo,PostRepository postRepo) {
        this.likeRepo = likeRepo;
        this.postRepo = postRepo;
    }

    public void create(long postId, AppUser user) {
        Post post = postRepo.findById(postId).orElseThrow(() -> ClientErrors.postNotFound(postId));
        if (!likeRepo.existsByPostIdAndUserName(postId,user.getUserName())) {
            if (!post.getUser().getUserName().equals(user.getUserName())) {
                PostLike like = new PostLike(user,post);
                post.getLikes().add(like);
                likeRepo.save(like);
                //postRepo.save(post);
            }else{
            throw ClientErrors.userLikeOwnPost();
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user already liked this post");
        }
    }

    public boolean isLiked(long postId, AppUser user) {
        if(!postRepo.existsById(postId)){
            throw ClientErrors.postNotFound(postId);
        }
        return likeRepo.existsByPostIdAndUserName(postId, user.getUserName());
    }

    public void delete(long postId, AppUser user) {
        if(!postRepo.existsById(postId)){
            throw ClientErrors.postNotFound(postId);
        }
        Optional<PostLike> like = likeRepo.findByPostIdAndUserName(postId, user.getUserName());
        like.ifPresent(likeRepo::delete);
    }


}

