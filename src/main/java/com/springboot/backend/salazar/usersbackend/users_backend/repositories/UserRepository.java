package com.springboot.backend.salazar.usersbackend.users_backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springboot.backend.salazar.usersbackend.users_backend.entities.User;


public interface UserRepository extends CrudRepository<User, Long> {

    Page<User> findAll(Pageable pageable);

    Optional<User> findByUsername(String username);

    List<User> findAllByDoctor_Id(Long doctorId);

    @Query("SELECT MAX(u.id) FROM User u")
    Long findLastUserId();

}
