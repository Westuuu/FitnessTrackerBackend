package com.fitnesstrackerbackend.core.database;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Component
public class DatabaseRoutingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            DbRole dbRole = determineDbRole(authentication.getAuthorities());
            DatabaseContextHolder.setRole(dbRole);
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            DatabaseContextHolder.clear();
        }

    }

    private DbRole determineDbRole(Collection<? extends GrantedAuthority> authorities) {
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            if ("ROLE_ADMIN".equals(role)) return DbRole.ADMIN;
            if ("ROLE_TRAINER".equals(role)) return DbRole.TRAINER;
        }

        return DbRole.TRAINEE;
    }
}
