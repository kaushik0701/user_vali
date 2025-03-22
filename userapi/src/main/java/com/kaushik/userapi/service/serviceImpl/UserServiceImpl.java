package com.kaushik.userapi.service.serviceImpl;

import com.kaushik.userapi.dto.SignInRequest;
import com.kaushik.userapi.dto.SignUpRequest;
import com.kaushik.userapi.dto.UserResponse;
import com.kaushik.userapi.entity.User;
import com.kaushik.userapi.repository.UserRepository;
import com.kaushik.userapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponse signUp(SignUpRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        userRepository.save(user);
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    @Override
    public UserResponse signIn(SignInRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .filter(u -> u.getPassword().equals(request.getPassword()))
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getName(), user.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    @Override
    public UserResponse updateUser(Long id, SignUpRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        userRepository.save(user);
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public void changePassword(Long id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getPassword().equals(oldPassword)) {
            throw new RuntimeException("Old password is incorrect");
        }
        user.setPassword(newPassword);
        userRepository.save(user);
    }
}
