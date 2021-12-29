package com.example.meshgrouptest.jwt;

import lombok.Data;

@Data
public class JwtValidationRequest {
    private static final long serialVersionUID = 5926468583005150707L;
    private String token;
}
