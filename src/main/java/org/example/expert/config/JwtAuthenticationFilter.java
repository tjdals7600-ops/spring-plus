package org.example.expert.config;


import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String bearerToken =
                request.getHeader("Authorization");

        if (bearerToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {

            String token =
                    jwtUtil.substringToken(bearerToken);

            Claims claims =
                    jwtUtil.extractClaims(token);

            Long userId =
                    Long.parseLong(claims.getSubject());

            String email =
                    claims.get("email", String.class);

            String nickname =
                    claims.get("nickname", String.class);

            UserRole userRole =
                    UserRole.valueOf(
                            claims.get("userRole", String.class)
                    );

            AuthUser principal =
                    new AuthUser(
                            userId,
                            email,
                            userRole,
                            nickname
                    );

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            principal,
                            null,
                            List.of(
                                    new SimpleGrantedAuthority(
                                            "ROLE_" + userRole.name()
                                    )
                            )
                    );

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

        } catch (Exception e) {
            log.error("JWT 인증 실패", e);
        }

        filterChain.doFilter(request, response);
    }
}
