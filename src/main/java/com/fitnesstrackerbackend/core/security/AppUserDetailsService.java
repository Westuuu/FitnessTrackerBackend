package com.fitnesstrackerbackend.core.security;

import com.fitnesstrackerbackend.domain.user.UserRepository;
import com.fitnesstrackerbackend.domain.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByLoginCredentialEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + userEntity.getUserType().name().toUpperCase())
        );

        return new User(
                userEntity.getLoginCredential().getEmail(),
                userEntity.getLoginCredential().getPasswordHash(),
                authorities
        );
    }
}
