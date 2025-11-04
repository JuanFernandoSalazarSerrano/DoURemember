package com.springboot.backend.salazar.usersbackend.users_backend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.springboot.backend.salazar.usersbackend.users_backend.entities.Role;
import java.util.Optional;


public interface RoleRepository extends CrudRepository<Role, Long>{

    Optional<Role> findByName(String name);

}
