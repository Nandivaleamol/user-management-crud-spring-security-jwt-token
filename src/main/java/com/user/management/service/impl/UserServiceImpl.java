package com.user.management.service.impl;

import com.user.management.entity.User;
import com.user.management.exception.ApiException;
import com.user.management.repository.UserRepository;
import com.user.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User addUser(User user) {

        var password = user.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(int userId) {
//        System.out.println("Method called from service layer");
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User Not Found With Id " + userId));
//        System.out.println("Method called - down");
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUserById(User user, int userId) {
        System.out.println("Update api called... this is confirmed from service layer");

        User existingUser = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User Not Found With Id " + userId));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setAddress(user.getAddress());
        existingUser.setMobile(user.getMobile());
        existingUser.setPassword(user.getPassword());
        existingUser.setRole(user.getRole());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUserById(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User Not Found With Id " + userId));
        userRepository.delete(user);

    }

    @Override
    public void login(String email, String password)  {
//        var user = userRepository.findUserByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User Not Found With Id "+email));
       var user = !email.isBlank()&&!password.isBlank()?userRepository.findUserByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User Not Found With Id "+email)): new ApiException("Username or password is empty!!");
    }


}
