package com.springboot.backend.salazar.usersbackend.users_backend.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.springboot.backend.salazar.usersbackend.users_backend.entities.MemoryRecall;

public interface MemoryRecallRepository extends CrudRepository<MemoryRecall, Long> {
        
    List<MemoryRecall> findAllByUserId(Long userId);
}
