package com.example.meshgrouptest.repositories;

import com.example.meshgrouptest.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
