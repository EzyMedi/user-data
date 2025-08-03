package com.EzyMedi.user.data.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {

    private UserCredential userCredential;

    public UserPrincipal(UserCredential userCredential) {
        this.userCredential = userCredential;
    }

    // Return user role (can enhance later to handle multiple roles)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    // Return hashed password from DB
    @Override
    public String getPassword() {
        return userCredential.getPasswordHash() != null ? userCredential.getPasswordHash() : "";
    }

    // Return account name as the username for authentication
    @Override
    public String getUsername() {
        return userCredential.getAccountName() != null ? userCredential.getAccountName() : "";
    }

    // These can be used to disable accounts (custom logic optional)
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
