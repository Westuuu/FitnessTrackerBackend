package com.fitnesstrackerbackend.domain.user;

import com.fitnesstrackerbackend.core.database.DatabaseContextHolder;
import com.fitnesstrackerbackend.core.database.DbRole;
import com.fitnesstrackerbackend.core.exception.ResourceNotFoundException;
import com.fitnesstrackerbackend.domain.user.dto.UserProfileDto;
import com.fitnesstrackerbackend.domain.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserProfileDto getUserProfile(Long userID) {
        DbRole currentRole = DatabaseContextHolder.getRole();

        UserEntity user = (currentRole == DbRole.ADMIN)
                ? userRepository.findByIdForAdmin(userID)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userID))
                : userRepository.findByIdForNonAdmin(userID)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userID));

        return userMapper.mapToUserProfileDto(user, currentRole);
    }
}
