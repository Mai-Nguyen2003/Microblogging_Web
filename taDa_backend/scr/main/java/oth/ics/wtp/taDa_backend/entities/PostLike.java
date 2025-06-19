package oth.ics.wtp.taDa_backend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class PostLike {
    @Id @GeneratedValue private long id;
    @ManyToOne @OnDelete(action = OnDeleteAction.CASCADE) private AppUser user;
    @ManyToOne @OnDelete(action = OnDeleteAction.CASCADE) private Post post;

    public PostLike() {
    }

    public PostLike(AppUser user, Post post) {
        this.user = user;
        this.post = post;
    }

    public long getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public AppUser getUser() {
        return user;
    }
}
