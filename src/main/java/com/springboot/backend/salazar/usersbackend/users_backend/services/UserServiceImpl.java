package com.springboot.backend.salazar.usersbackend.users_backend.services;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.backend.salazar.usersbackend.users_backend.entities.GroundTruthResponse;
import com.springboot.backend.salazar.usersbackend.users_backend.entities.Role;
import com.springboot.backend.salazar.usersbackend.users_backend.entities.User;
import com.springboot.backend.salazar.usersbackend.users_backend.models.Iuser;
import com.springboot.backend.salazar.usersbackend.users_backend.models.UserRequest;
import com.springboot.backend.salazar.usersbackend.users_backend.repositories.GroundTruthRepository;
import com.springboot.backend.salazar.usersbackend.users_backend.repositories.RoleRepository;
import com.springboot.backend.salazar.usersbackend.users_backend.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private GroundTruthRepository groundTruthRepository;

    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true) // from springframework NOt jakarta
    public List<User> findAll() {
        return (List) this.repository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(@NonNull Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional()
    public User saveAdmin(@NonNull User user) {

        List<Role> roles = new ArrayList<>();


        Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
        optionalRoleAdmin.ifPresent(roles::add);

        user.setRoles(roles);
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }


        @Override
    @Transactional()
    public User save(@NonNull User user) {

        List<Role> roles = new ArrayList<>();
        Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");

        optionalRoleUser.ifPresent(role -> roles.add(role)); // simpler way,  optionalRoleUser.ifPresent(roles::add);


        if (user.isAdmin()) {

            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }

        user.setRoles(roles);
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }


    @Override
    @Transactional()
    public void deleteById(@NonNull Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public Page<GroundTruthResponse> findAllByUserId(Long id, Pageable pageable) {
        return this.groundTruthRepository.findAllByUsergroundTruthResponse_Id(id, pageable);
    }

    @Override
    public List<GroundTruthResponse> findAllSessionsByUserId(Long id){
        return this.groundTruthRepository.findAllByUsergroundTruthResponse_Id(id);
    }

    @Transactional
    @Override
    public Optional<User> update(UserRequest user, Long id) {

        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isPresent()){
            User userDb = userOptional.orElseThrow();
            userDb.setEmail(user.getEmail());
            userDb.setLastname(user.getLastname());
            userDb.setName(user.getName());
            userDb.setUsername(user.getUsername());

            userDb.setRoles(getRoles(user));
            return Optional.of(repository.save(userDb));
        }

        return Optional.empty();

    }


    private List<Role> getRoles(Iuser user) {

        List<Role> roles = new ArrayList<>();
        Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");

        optionalRoleUser.ifPresent(role -> roles.add(role)); // simpler way,  optionalRoleUser.ifPresent(roles::add);

        if (user.isAdmin()) {

            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }
        return roles;
    }


    @Transactional
    @Override
    public Optional<User> updateAdmin(UserRequest user, Long id) {

        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isPresent()){
            User userDb = userOptional.orElseThrow();
            userDb.setEmail(user.getEmail());
            userDb.setLastname(user.getLastname());
            userDb.setName(user.getName());
            userDb.setUsername(user.getUsername());

            userDb.setRoles(getRolesAdmin(user));
            return Optional.of(repository.save(userDb));
        }

        return Optional.empty();

    }


        private List<Role> getRolesAdmin(Iuser user) {

        List<Role> roles = new ArrayList<>();

        Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
        optionalRoleAdmin.ifPresent(roles::add);

        return roles;
    }





    
}
