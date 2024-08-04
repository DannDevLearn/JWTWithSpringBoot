package com.example.jwt.services;

import com.example.jwt.model.dto.CreateUserDTO;
import com.example.jwt.model.entity.RoleEntity;
import com.example.jwt.model.entity.UserEntity;
import com.example.jwt.model.enums.EnumRole;
import com.example.jwt.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CreateUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void newUser(CreateUserDTO userDTO){

        Set<RoleEntity> roles = userDTO
                .getRoles().stream()
                .map(role -> RoleEntity.builder()
                        .roleName(EnumRole.valueOf(role))
                        .build())
                .collect(Collectors.toSet());


        UserEntity userEntity = UserEntity.builder()
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .roles(roles)
                .build();
        userRepository.save(userEntity);
    }

}
