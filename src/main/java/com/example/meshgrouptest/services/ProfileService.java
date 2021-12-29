package com.example.meshgrouptest.services;


import com.example.meshgrouptest.models.Phone;
import com.example.meshgrouptest.models.Profile;
import com.example.meshgrouptest.repositories.ProfileRepository;
import com.example.meshgrouptest.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class ProfileService {
    @Autowired
    ProfileRepository profileRepository;

    BigDecimal multiplier = new BigDecimal("1.1");
    BigDecimal maxMultiplier = new BigDecimal("2.07");
    public void cashIncrease(){
        List<Profile> profileList = profileRepository.findAll();
        for(Profile profile : profileList){
            log.info("Profile with id {}", profile.getId());
            if(profile.getCash().compareTo(profile.getInitialCash().multiply(maxMultiplier)) == 0){
                log.info("Profile cash is already max possible: {}", profile.getCash());
                continue;
            }
            if(profile.getCash().multiply(multiplier).compareTo(profile.getInitialCash().multiply(maxMultiplier)) == 1){
                profile.setCash(profile.getInitialCash().multiply(maxMultiplier));
                profileRepository.save(profile);
                log.info("Profile cash is above maximum, setting to max possible: {}", profile.getCash());
                continue;
            }
            profile.setCash(profile.getCash().multiply(multiplier));
            profileRepository.save(profile);
            log.info("Profile cash increased by 10% to {}",profile.getCash());
        }
    }
}
