package com.fitnesstrackerbackend.domain.user;

import com.fitnesstrackerbackend.domain.user.dto.UserProfileDto;
import com.fitnesstrackerbackend.domain.user.dto.UserRegistrationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    public ResponseEntity<UserProfileDto> getMyProfile()

    @GetMapping("/{userID}")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable Long userID) {
        return ResponseEntity.ok(userService.getUserProfile(userID));
    }

    @PostMapping
    public ResponseEntity<UserProfileDto> registerUser(@RequestBody @Valid UserRegistrationDto registrationDto) {

        UserProfileDto createdUser = userService.registerUser(registrationDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userID}")
                .buildAndExpand(createdUser.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(createdUser);
    }
}
