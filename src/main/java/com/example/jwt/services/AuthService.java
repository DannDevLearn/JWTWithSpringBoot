package com.example.jwt.services;

import com.example.jwt.config.security.JwtUtils;
import com.example.jwt.model.dto.AuthDTO;
import com.example.jwt.model.dto.LoginDTO;
import com.example.jwt.model.entity.UserEntity;
import com.example.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthDTO login(LoginDTO loginDTO){
        try {
            authenticate(loginDTO.getEmail(), loginDTO.getPassword());
            UserEntity userEntity = userRepository.findByEmail(loginDTO.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            String token = jwtUtils.generateAccessToken(userEntity.getEmail());
            return new AuthDTO(token);

        }catch (BadCredentialsException | UsernameNotFoundException e){
            log.info("Exception bad credentials: {}", e.getMessage());
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    private void authenticate(String username, String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
