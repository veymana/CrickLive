package com.crick.livecrick.service;

import com.crick.livecrick.dto.AdminDashboardDto;
import com.crick.livecrick.dto.UpdateRoleRequest;
import com.crick.livecrick.dto.UserProfileDto;
import com.crick.livecrick.entity.User;
import com.crick.livecrick.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AdminDashboardDto getDashboard() {
        List<User> allUsers = userRepository.findAll();
        long totalUsers = allUsers.size();
        long adminUsers = allUsers.stream().filter(u -> "ADMIN".equals(u.getRole())).count();
        long regularUsers = totalUsers - adminUsers;
        long activeUsers = allUsers.stream().filter(User::isActive).count();

        return new AdminDashboardDto(totalUsers, adminUsers, regularUsers, activeUsers);
    }

    public List<UserProfileDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserProfileDto(
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        user.getRole(),
                        user.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    public UserProfileDto updateUserRole(Long userId, UpdateRoleRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(request.role());
        User updatedUser = userRepository.save(user);

        return new UserProfileDto(
                updatedUser.getId(),
                updatedUser.getEmail(),
                updatedUser.getName(),
                updatedUser.getRole(),
                updatedUser.getCreatedAt()
        );
    }

    public void deleteUser(Long userId) {
        if (!userRepository.findById(userId).isPresent()) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(userId);
    }
}

