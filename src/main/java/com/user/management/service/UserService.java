package com.user.management.service;

import com.user.management.entity.User;

import java.util.List;

public interface UserService {

    // add user service method
    User addUser(User user);

    // get user by id service method
    User getUserById(int userId);

    // get all users service method
    List<User> getAllUsers();

    // update user by user id
    User updateUserById(User user, int userId);

    // delete user by user id
    void deleteUserById(int userId);

    // login service method
    void login(String email, String password);
}
