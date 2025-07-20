package com.EzyMedi.user.data.model;

import com.EzyMedi.user.data.constants.Role;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "app_user")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "userId"
)
public class User {
    @Id
    @GeneratedValue
    private UUID userId;

    @Column
    private String fullName;

    @Column
    private String gender;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany
    @JoinTable(
            name = "followers",
            joinColumns = @JoinColumn(name = "user_id"),            // this user's id
            inverseJoinColumns = @JoinColumn(name = "follower_id")   // follower's id
    )
    private List<User> followers;

    @ManyToMany(mappedBy = "followers")
    private List<User> following;

    public void addFollower(User toFollow) {
        following.add(toFollow);
        toFollow.getFollowers().add(this);
    }

    public void removeFollower(User toFollow) {
        following.remove(toFollow);
        toFollow.getFollowers().remove(this);
    }
}
