package com.example.meshgrouptest.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private List<UserDTO> users;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
