package com.user.management.controller;

import com.user.management.entity.User;
import com.user.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@RestController
@EnableMethodSecurity
@EnableWebMvc
@RequestMapping(value = "/api/v1/users")
@CrossOrigin("*")
   public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
//    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<User> addUser(@RequestBody User user){
        return ResponseEntity.ok(userService.addUser(user));
    }

    @GetMapping(value = "/{userId}")
//    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<User> getUserById(@PathVariable int userId){
        System.out.println("ID "+userId);
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    // adding role based authentication

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_MANAGER') and hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping(value = "/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<User> updateUserById(@RequestBody User user, @PathVariable int userId ){
        System.out.println("Update api called...  this is confirmed from controller");
        return ResponseEntity.ok(userService.updateUserById(user,userId));
    }

   @DeleteMapping(value = "/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
//   @CrossOrigin(origins = "http://localhost:3000")
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable int userId){
        try{
            userService.deleteUserById(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
