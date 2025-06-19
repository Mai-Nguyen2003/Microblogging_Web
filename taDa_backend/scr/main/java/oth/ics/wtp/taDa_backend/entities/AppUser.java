package oth.ics.wtp.taDa_backend.entities;

import jakarta.persistence.*;
import java.util.List;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.EAGER;

@Entity
public class AppUser {
    @Id private String name;
    private String hashedPassword;
    private String fullName;
    private String email;
    private String biography;
    @OneToMany(mappedBy = "user", fetch = EAGER, cascade = REMOVE) private List<Post> posts;
    @OneToMany(mappedBy = "followed", fetch = EAGER, cascade = REMOVE) private List<Follow> followers;
    @OneToMany(mappedBy = "follower", fetch = EAGER, cascade = REMOVE) private List<Follow> followings;

    public AppUser() {
    }

    public AppUser(String name, String hashedPassword) {
        this.name = name;
        this.hashedPassword = hashedPassword;
    }


    public List<Post> getPosts() {
        return posts;
    }

    public String getUserName() {
        return name;
    }

    public String getBiography() {
        return biography;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public List<Follow> getFollowers() {
        return followers;
    }

    public List<Follow> getFollowings() {
        return followings;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

}

