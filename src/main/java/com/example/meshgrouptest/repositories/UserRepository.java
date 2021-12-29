package com.example.meshgrouptest.repositories;

import com.example.meshgrouptest.models.Phone;
import com.example.meshgrouptest.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    List<User> findAllByAgeGreaterThanEqual(int age);
    List<User> findAllByPhones(Phone phone);
    Page<User> findAllByAge(int age, Pageable pageable);
    Page<User> findAllByNameLike(String name, Pageable pageable);
    Page<User> findAllByEmail(String email, Pageable pageable);
    Page<User> findAllByPhones(Phone phone,Pageable pageable);
}
