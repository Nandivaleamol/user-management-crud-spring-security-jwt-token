package com.user.management.controller;

import com.user.management.entity.User;
import com.user.management.exception.ApiException;
import com.user.management.payload.JwtAuthRequest;
import com.user.management.payload.JwtAuthResponse;
import com.user.management.security.JwtTokenHelper;
import com.user.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/auth")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    //login api
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
        authenticate(request.getUsername(),request.getPassword());
        var userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        var token = jwtTokenHelper.generateToken(userDetails);
        var response = new JwtAuthResponse();
        response.setToken(token);
        response.setUser((User) userDetails);
        User user = response.getUser();
//        System.out.println(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (BadCredentialsException e){
            System.out.println("Invalid Details!!");
            throw new ApiException("Invalid username or password!!");
        }
    }
    // register new user
    @PostMapping("/register")
    public ResponseEntity<User> registerNewUser(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.addUser(user));
    }

}
