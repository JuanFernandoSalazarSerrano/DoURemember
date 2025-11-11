package com.springboot.backend.salazar.usersbackend.users_backend.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.springboot.backend.salazar.usersbackend.users_backend.entities.GroundTruthResponse;

public interface GroundTruthRepository extends CrudRepository<GroundTruthResponse, Long> {

    Page<GroundTruthResponse> findAllByUsergroundTruthResponse_Id(Long userId, Pageable pageable);

    List<GroundTruthResponse> findAllByUsergroundTruthResponse_Id(Long userId);
    
}
