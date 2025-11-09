package com.springboot.backend.salazar.usersbackend.users_backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import static jakarta.persistence.GenerationType.*;

@Entity
@Table(name = "GroundTruthAnswers")
public class GroundTruthAnswer {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String userAnswer;

    public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUserAnswer() {
            return userAnswer;
        }

        public void setUserAnswer(String userAnswer) {
            this.userAnswer = userAnswer;
        }
}
