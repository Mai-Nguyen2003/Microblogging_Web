package oth.ics.wtp.taDa_backend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Follow {
    @Id @GeneratedValue private long id;
    @ManyToOne @OnDelete(action = OnDeleteAction.CASCADE) private AppUser follower;
    @ManyToOne @OnDelete(action = OnDeleteAction.CASCADE) private AppUser followed;

    public Follow() {
    }

    public Follow(AppUser follower, AppUser followed) {
        this.follower = follower;
        this.followed = followed;
    }

    public long getId() {
        return id;
    }

    public AppUser getFollowed() {
        return followed;
    }

    public AppUser getFollower() {
        return follower;
    }
}
