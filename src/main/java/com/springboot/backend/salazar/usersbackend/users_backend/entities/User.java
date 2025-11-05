package com.springboot.backend.salazar.usersbackend.users_backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.backend.salazar.usersbackend.users_backend.models.Iuser;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

// security concern if i add 

@Entity
@Table(name = "users")
public class User implements Iuser {
    
    @Id
    @GeneratedValue(strategy = IDENTITY) //autoincrement
    private Long id;

    @NotBlank
    private String name;

    @NotEmpty
    private String lastname;

    @NotEmpty
    private String profilepicture;

    @NotEmpty
    private String condition;

    @NotEmpty
    private String carer;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String username;

    @Transient // trasient means its not part of the persistence only class, not database column!!
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // when we send in the json this value with true or false, this allows us to poblate with the user request
    private boolean admin;

    @NotEmpty
    private String password;

    @JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "users_roles",
        joinColumns = {@JoinColumn(name ="user_id")},
        inverseJoinColumns = @JoinColumn(name="role_id"),
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})}
    )

    private List<Role> roles;

        public boolean isAdmin() {
        return admin;
    }

    @OneToMany(mappedBy = "user")
    private List<MemoryRecall> memoryRecalls;


    public void setAdmin(boolean admin) {
        this.admin = admin;
    }


    public User() {
        this.roles = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


        public String getProfilepicture() {
        return profilepicture;
    }


    public void setProfilepicture(String profilepicture) {
        this.profilepicture = profilepicture;
    }


    public String getCondition() {
        return condition;
    }


    public void setCondition(String condition) {
        this.condition = condition;
    }


    public String getCarer() {
        return carer;
    }


    public void setCarer(String carer) {
        this.carer = carer;
    }
    
}