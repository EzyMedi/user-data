package com.EzyMedi.user.data.repository;

import com.EzyMedi.user.data.constants.Role;
import com.EzyMedi.user.data.model.User;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findAllUsersByRole(@Param("role") Role role);
}
