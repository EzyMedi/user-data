package com.EzyMedi.user.data.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "user_credential")
public class UserCredential {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private UUID credentialId;

    @Column(unique = true, nullable = false)
    private String accountName;

    @Column(nullable = false)
    private String passwordHash;
}
