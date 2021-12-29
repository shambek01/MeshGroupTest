package com.example.meshgrouptest.services;

import com.example.meshgrouptest.dto.UserDTO;
import com.example.meshgrouptest.dto.UserResponse;
import com.example.meshgrouptest.models.Phone;
import com.example.meshgrouptest.models.Profile;
import com.example.meshgrouptest.models.User;
import com.example.meshgrouptest.repositories.PhoneRepository;
import com.example.meshgrouptest.repositories.ProfileRepository;
import com.example.meshgrouptest.repositories.UserRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    PhoneRepository phoneRepository;

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public boolean saveUser(UserDTO userDTO) throws Exception {
        if(userRepository.findByEmail(userDTO.getEmail()) != null){
            log.info("Email already exists");
            return false;
        }
        User user = new User();
        user.setAge(userDTO.getAge());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        userRepository.saveAndFlush(user);

        Profile profile = new Profile();
        profile.setCash(userDTO.getCash());
        profile.setInitialCash(userDTO.getCash());
        profile.setUser(user);
        profileRepository.save(profile);


        for(String phoneValue : userDTO.getPhones()){
            if(phoneRepository.findByValue(phoneValue) != null){
                log.info("Phone value already exists");
                throw new Exception("Phone value already exists");
            }
            Phone phone = new Phone();
            phone.setValue(phoneValue);
            phone.setUser(user);
            phoneRepository.save(phone);
        }
        log.info("New user added with name {}", user.getName());
        return true;
    }

    public User getByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public List<User> getByAgeGreaterThan(int age){
        return userRepository.findAllByAgeGreaterThanEqual(age);
    }
    public List<User> getByPhonesEqualTo(String phone){
        return userRepository.findAllByPhones(phoneRepository.findByValue(phone));
    }
    public boolean updateEmail(User user, String email){
        if(userRepository.findByEmail(email) != null){
            return false;
        };
        user.setEmail(email);
        userRepository.save(user);
        return true;
    }

    public UserResponse getAllUsersFiltered(Page<User> users) {
        // get content for page object
        List<User> listOfUsers = users.getContent();

        List<UserDTO> usersDTO= listOfUsers.stream().map(user -> mapToDTO(user)).collect(Collectors.toList());

        UserResponse userResponse = new UserResponse();
        userResponse.setUsers(usersDTO);
        userResponse.setPageNo(users.getNumber());
        userResponse.setPageSize(users.getSize());
        userResponse.setTotalElements(users.getTotalElements());
        userResponse.setTotalPages(users.getTotalPages());
        userResponse.setLast(users.isLast());

        return userResponse;
    }

    // convert Entity into DTO
    private UserDTO mapToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhones(new ArrayList<>());
        List<Phone> phones = user.getPhones();
        for(Phone phone : phones) {
            userDTO.getPhones().add(phone.getValue());
        }
        userDTO.setCash(user.getProfile().getCash());
        return userDTO;
    }



}


