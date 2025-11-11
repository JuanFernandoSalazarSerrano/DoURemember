package com.springboot.backend.salazar.usersbackend.users_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.backend.salazar.usersbackend.users_backend.entities.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
