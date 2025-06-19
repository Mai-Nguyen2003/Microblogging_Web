package oth.ics.wtp.taDa_backend.entities;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import org.hibernate.annotations.*;

import java.time.Instant;
import java.util.List;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
public class Post {
    @Id @GeneratedValue private long id;
    private String textMessage;
    private Instant createdAt;
    @ManyToOne @OnDelete(action = CASCADE) private AppUser user;
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) List<Comment> comments;
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) List<PostLike> likes;

    public Post() {
    }

    public Post(String textMessage, Instant createdAt, AppUser user) {
        this.textMessage = textMessage;
        this.createdAt = createdAt;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public AppUser getUser() {
        return user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<PostLike> getLikes() {
        return likes;
    }
}
