package com.springboot.backend.salazar.usersbackend.users_backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "doctors")
public class Doctor{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "doctorname", length = 45)
    private String doctorname;

    @Column(name = "doctorlocation", length = 45)
    private String doctorlocation;

    @Column(name = "doctorspecialties", length = 45)
    private String doctorspecialties;

    @Column(name = "isremote")
    private Boolean isremote;

    @OneToMany(mappedBy = "doctor")
    private List<User> users;


    // Getters & setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getDoctorlocation() {
        return doctorlocation;
    }

    public void setDoctorlocation(String doctorlocation) {
        this.doctorlocation = doctorlocation;
    }

    public String getDoctorspecialties() {
        return doctorspecialties;
    }

    public void setDoctorspecialties(String doctorspecialties) {
        this.doctorspecialties = doctorspecialties;
    }

    public Boolean getIsremote() {
        return isremote;
    }

    public void setIsremote(Boolean isremote) {
        this.isremote = isremote;
    }
}
