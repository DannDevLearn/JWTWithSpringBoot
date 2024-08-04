package com.example.jwt.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/private")
public class PrincipalPrivateController {

    @GetMapping("/hello")
    public ResponseEntity<String> getHello(){
        return ResponseEntity.ok("Hello world in my private endpoint");
    }

    @PostMapping("/hello")
    public ResponseEntity<List<String>> postHello(){
        List<String> l = List.of("Hello world", "It's private", "EndPoint");
        return ResponseEntity.ok(l);
    }

}
