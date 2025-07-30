package com.EzyMedi.user.data.repository;

import com.EzyMedi.user.data.model.User;
import com.EzyMedi.user.data.model.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserCredentialRepository extends JpaRepository<UserCredential, UUID> {
    UserCredential findByCredentialId(UUID userId);
    boolean existsByAccountName(String accountName);
    UserCredential findByAccountName(String accountName);
}
