package com.springboot.backend.salazar.usersbackend.users_backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.backend.salazar.usersbackend.users_backend.entities.User;
import com.springboot.backend.salazar.usersbackend.users_backend.repositories.UserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Transactional(readOnly = true)
    @Override
    
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = repository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

    return new CustomUserDetails(user);
}

}
