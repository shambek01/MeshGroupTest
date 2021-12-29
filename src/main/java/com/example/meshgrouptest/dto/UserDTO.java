package com.example.meshgrouptest.dto;

import com.example.meshgrouptest.models.Phone;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private int age;
    private String email;
    private List<String> phones;
    private BigDecimal cash;
}
