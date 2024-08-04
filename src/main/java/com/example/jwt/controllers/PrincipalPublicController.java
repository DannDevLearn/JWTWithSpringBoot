package com.example.jwt.controllers;

import com.example.jwt.model.dto.CreateUserDTO;
import com.example.jwt.services.CreateUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PrincipalPublicController {

    private final CreateUserService createUserService;

    public PrincipalPublicController(CreateUserService createUserService) {
        this.createUserService = createUserService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> getHello(){
        return ResponseEntity.ok("Hello world in my public endpoint");
    }


    @PostMapping("/create-user")
    public ResponseEntity<Void> crateUser(@RequestBody CreateUserDTO userDTO){
        createUserService.newUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
