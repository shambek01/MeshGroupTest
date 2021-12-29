package com.example.meshgrouptest.controllers;

import com.example.meshgrouptest.dto.UserDTO;
import com.example.meshgrouptest.dto.UserResponse;
import com.example.meshgrouptest.jwt.JwtRequest;
import com.example.meshgrouptest.jwt.JwtTokenUtil;
import com.example.meshgrouptest.models.Phone;
import com.example.meshgrouptest.models.User;
import com.example.meshgrouptest.repositories.PhoneRepository;
import com.example.meshgrouptest.repositories.UserRepository;
import com.example.meshgrouptest.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@Slf4j
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PhoneRepository phoneRepository;
    @PostMapping(path = "/adduser")
    public String addUser(@RequestBody UserDTO userDTO) {
        try {
            userService.saveUser(userDTO);
        }catch (Exception e){
            log.info(e.getMessage());
            return e.getMessage();
        }
        return "Added successfully";
    }

    @PostMapping(path = "/updateemail")
    public String updateEmail(@RequestBody JwtRequest request, @RequestHeader(name = "Authentication") String token){
        if(jwtTokenUtil.validateToken(token)) {
            if (userService.updateEmail(userService.getByEmail(jwtTokenUtil.getEmailFromToken(token)), request.getEmail())) {
                log.info("Updated user email to {}", request.getEmail());
                log.info("Please, generate new token for new email");
                String newToken = jwtTokenUtil.generateToken(userService.getByEmail(request.getEmail()));
                return "Updated successfully, please use new token for new email:\n"+ newToken;
            }
        }
        log.info("Failed updating user email");
        return "Failed to update email";
    }
    @GetMapping(path = "/allusers")
    public UserResponse getAllUsers( @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                     @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
                                     @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                     @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
                                     @RequestParam(value = "age", defaultValue = "-1", required = false) int age,
                                     @RequestParam(value = "name", defaultValue = "", required = false) String name,
                                     @RequestParam(value = "email", defaultValue = "", required = false) String email,
                                     @RequestParam(value = "phone", defaultValue = "", required = false) String phone){

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<User> users = null;
        //Фильтрация по колонкам, доступна только по одной из вариантов
        if(age != -1){
            users = userRepository.findAllByAge(age,pageable);
        }
        else if(!name.equals("")){
            System.out.println(name);
            users = userRepository.findAllByNameLike(name,pageable);
        }
        else if(!email.equals("")){
            System.out.println(email);
            users = userRepository.findAllByEmail(email,pageable);
        }
        else if(!phone.equals("")){
            System.out.println(phone);
            Phone phoneObj = phoneRepository.findByValue(phone);
            users = userRepository.findAllByPhones(phoneObj, pageable);
        }
        else{
            users = userRepository.findAll(pageable);
        }
        return userService.getAllUsersFiltered(users);
    }

}
