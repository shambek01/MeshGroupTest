package com.example.meshgrouptest;

import com.example.meshgrouptest.models.User;
import com.example.meshgrouptest.services.ProfileService;
import com.example.meshgrouptest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@SpringBootApplication
@EnableScheduling
public class MeshGroupTestApplication {
    @Autowired
    ProfileService profileService;
    @Autowired
    UserService userService;
    public static void main(String[] args) {
        SpringApplication.run(MeshGroupTestApplication.class, args);
    }
    @Scheduled(fixedRate = 20000)
    public void increaseCash(){
        profileService.cashIncrease();
    }
}
