package com.springboot.backend.salazar.usersbackend.users_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import com.springboot.backend.salazar.usersbackend.users_backend.entities.User;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(@NonNull Long id);

    User save(@NonNull User user);

    void deleteById(@NonNull Long id);

    Page<User> findAll(Pageable pageable);


}
