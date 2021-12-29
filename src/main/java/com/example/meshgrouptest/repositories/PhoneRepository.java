package com.example.meshgrouptest.repositories;

import com.example.meshgrouptest.models.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    Phone findByValue(String value);
}
