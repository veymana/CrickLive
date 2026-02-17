package com.crick.livecrick.service;

import com.crick.livecrick.dto.UpdateProfileRequest;
import com.crick.livecrick.dto.UserProfileDto;
import com.crick.livecrick.entity.User;
import com.crick.livecrick.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserProfileDto getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserProfileDto(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.getCreatedAt()
        );
    }

    public UserProfileDto updateProfile(String email, UpdateProfileRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.name() != null) {
            user.setName(request.name());
        }
        if (request.email() != null && !request.email().equals(email)) {
            if (userRepository.existsByEmail(request.email())) {
                throw new RuntimeException("Email already in use");
            }
            user.setEmail(request.email());
        }

        User updatedUser = userRepository.save(user);

        return new UserProfileDto(
                updatedUser.getId(),
                updatedUser.getEmail(),
                updatedUser.getName(),
                updatedUser.getRole(),
                updatedUser.getCreatedAt()
        );
    }
}

