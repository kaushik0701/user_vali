package com.kaushik.userapi.service;

import com.kaushik.userapi.dto.SignInRequest;
import com.kaushik.userapi.dto.SignUpRequest;
import com.kaushik.userapi.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse signUp(SignUpRequest request);

    UserResponse signIn(SignInRequest request);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, SignUpRequest request);

    void deleteUser(Long id);

    void changePassword(Long id, String oldPassword, String newPassword);

    UserResponse getUserById(Long id);
}
