package com.EzyMedi.user.data.service;

import com.EzyMedi.user.data.model.UserCredential;
import com.EzyMedi.user.data.model.UserPrincipal;
import com.EzyMedi.user.data.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserCredentialRepository userCredentialRepository;
    @Override
    public UserDetails loadUserByUsername(String accountName) throws UsernameNotFoundException {
        UserCredential userCredential = userCredentialRepository.findByAccountName(accountName);
        if (userCredential == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(userCredential);
    }
}
