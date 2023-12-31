package com.hmcuong.fooddelivery.config;

import com.hmcuong.fooddelivery.entity.Token.Token;
import com.hmcuong.fooddelivery.entity.Token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt;
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt =authHeader.substring(7);
        var storedToken =tokenRepository.findByToken(jwt);
        if(storedToken.isPresent()) {
            Token token = storedToken.get();
            token.setExpired(true);
            token.setRevoked(true);
            tokenRepository.save(token);
            SecurityContextHolder.clearContext();
        }
    }
}
