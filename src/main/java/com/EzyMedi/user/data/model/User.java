package com.EzyMedi.user.data.model;

import com.EzyMedi.user.data.constants.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;
@Entity
@Data
@Table(name = "user")
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
}
