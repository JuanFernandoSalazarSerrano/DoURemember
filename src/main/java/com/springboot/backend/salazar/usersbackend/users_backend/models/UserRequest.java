package com.springboot.backend.salazar.usersbackend.users_backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class UserRequest implements Iuser {

    @NotBlank
    private String name;

    @NotEmpty
    private String lastname;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String username;

    @Transient // trasient means its not part of the persistence only class, not database column!!
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // when we send in the json this value with true or false, this allows us to poblate with the user request
    private boolean admin;

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
