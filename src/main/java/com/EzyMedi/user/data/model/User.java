package com.EzyMedi.user.data.model;

import com.EzyMedi.user.data.constants.Role;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "app_user")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "userId"
)
public class User {
    @Id
    private UUID userId;

    @Column
    private String gender;

    @Column(unique = true, length = 100)
    private String email;

    @Column
    private String phone;

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

    public User(UUID userId, Role role) {
        this.userId = userId;
        this.role = role;
    }
    public void addFollower(User toFollow) {
        following.add(toFollow);
        toFollow.getFollowers().add(this);
    }

    public void removeFollower(User toFollow) {
        following.remove(toFollow);
        toFollow.getFollowers().remove(this);
    }
}
