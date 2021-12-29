package com.example.meshgrouptest.controllers;

import com.example.meshgrouptest.jwt.JwtRequest;
import com.example.meshgrouptest.jwt.JwtResponse;
import com.example.meshgrouptest.jwt.JwtTokenUtil;
import com.example.meshgrouptest.jwt.JwtValidationRequest;
import com.example.meshgrouptest.models.User;
import com.example.meshgrouptest.services.UserService;
import io.jsonwebtoken.SignatureException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class AuthenticationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @PostMapping(path = "/validate")
    public boolean validateToken(@RequestBody JwtValidationRequest token){
        try {
            jwtTokenUtil.validateToken(token.getToken());
            log.info("Validated user is {}", jwtTokenUtil.getEmailFromToken(token.getToken()));
        }
        catch(SignatureException e){
            log.info(e.getMessage());
            return false;
        }
        return true;
    }

    @PostMapping(path = "/auth")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest){

        //Кастомная аутентификация с использованием эл.адреса без пароля
        if(userService.getByEmail(authenticationRequest.getEmail()) == null){
            return ResponseEntity.ok("Email not found");
        }

        final User user = userService.getByEmail(authenticationRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(user);
        log.info("User authenticated {}",token);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
