package com.springboot.backend.salazar.usersbackend.users_backend.services;

import com.springboot.backend.salazar.usersbackend.users_backend.entities.GroundTruthResponse;

import io.micrometer.common.lang.NonNull;

public interface GroundTruthService {

    void generateGroundTruthTestSendAnswer(String answer);

    GroundTruthResponse saveGroundTruthResponse(@NonNull GroundTruthResponse groundTruthResponse);


}
