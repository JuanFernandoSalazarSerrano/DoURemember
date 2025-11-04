package com.springboot.backend.salazar.usersbackend.users_backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Table;


@Entity
@Table(name = "memoryrecalls")
public class MemoryRecall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memoryrecallid")
    private Long id;

    @Column(name = "type", length = 200)
    private String type;

    @Column(name = "memoryrecallurl", length = 200)
    private String memoryrecallurl;

    @Column(name = "groundtruthdescriptionsmall", length = 45)
    private String groundtruthdescriptionsmall;

    @Column(name = "groundtruthdescriptioncomplete", length = 200)
    private String groundtruthdescriptioncomplete;

    @Column(name = "groundtruthfacts", length = 200)
    private String groundtruthfacts;

    @Column(name = "keyentities", length = 45)
    private String keyEntities;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MemoryRecall() {
        // Default constructor required by JPA
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMemoryrecallurl() {
        return memoryrecallurl;
    }

    public void setMemoryrecallurl(String memoryrecallurl) {
        this.memoryrecallurl = memoryrecallurl;
    }

    public String getGroundtruthdescriptionsmall() {
        return groundtruthdescriptionsmall;
    }

    public void setGroundtruthdescriptionsmall(String groundtruthdescriptionsmall) {
        this.groundtruthdescriptionsmall = groundtruthdescriptionsmall;
    }

    public String getGroundtruthdescriptioncomplete() {
        return groundtruthdescriptioncomplete;
    }

    public void setGroundtruthdescriptioncomplete(String groundtruthdescriptioncomplete) {
        this.groundtruthdescriptioncomplete = groundtruthdescriptioncomplete;
    }

    public String getGroundtruthfacts() {
        return groundtruthfacts;
    }

    public void setGroundtruthfacts(String groundtruthfacts) {
        this.groundtruthfacts = groundtruthfacts;
    }

    public String getKeyEntities() {
        return keyEntities;
    }

    public void setKeyEntities(String keyEntities) {
        this.keyEntities = keyEntities;
    }

    
}
