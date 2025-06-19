package oth.ics.wtp.taDa_backend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
public class Comment {
    @Id @GeneratedValue private long id;
    private String textComment;
    private Instant createdAt;
    @ManyToOne @OnDelete(action = OnDeleteAction.CASCADE) private AppUser user;
    @ManyToOne @OnDelete(action = OnDeleteAction.CASCADE) private Post post;

    public Comment() {
    }

    public Comment(String textComment, Instant createdAt, AppUser user, Post post) {
        this.textComment = textComment;
        this.createdAt = createdAt;
        this.user = user;
        this.post = post;
    }

    public long getId() {
        return id;
    }

    public String getTextComment() {
        return textComment;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public AppUser getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }


}
