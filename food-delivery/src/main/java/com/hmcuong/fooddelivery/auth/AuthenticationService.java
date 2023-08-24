package com.hmcuong.fooddelivery.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmcuong.fooddelivery.auth.Register.ResponseRegister;
import com.hmcuong.fooddelivery.auth.Register.RegisterRequest;
import com.hmcuong.fooddelivery.config.CustomUser;
import com.hmcuong.fooddelivery.config.JWT.JwtService;
import com.hmcuong.fooddelivery.entity.Token.Token;
import com.hmcuong.fooddelivery.entity.Token.TokenRepository;
import com.hmcuong.fooddelivery.entity.Token.TokenType;
import com.hmcuong.fooddelivery.entity.User.User;
import com.hmcuong.fooddelivery.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public ResponseRegister register(RegisterRequest registerRequest) {
        // Check UserName đã tồn tại hay chưa
        Optional<User> userOptional = userRepository.findByUserName(registerRequest.getUserName());
        if(userOptional.isEmpty()){
            // Check Email đã tồn tại hay chưa
            if(userRepository.findByEmail(registerRequest.getEmail()).isEmpty()){
                userRepository.save(
                    User
                        .builder()
                        .id(java.util.UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10))
                        .fullName(registerRequest.getFullName())
                        .userName(registerRequest.getUserName())
                        .email(registerRequest.getEmail())
                        .password(passwordEncoder().encode(registerRequest.getPassword()))
                        .role(registerRequest.getRole())
                        .build()
                );
                return ResponseRegister.builder().registerRequest(registerRequest).msg("create user successfully").build();
            }
            return ResponseRegister.builder().registerRequest(registerRequest).msg("email already exists").build();
        }
        return ResponseRegister.builder().registerRequest(registerRequest).msg("username already exists").build();
    }

    public AuthenticateResponse authenticate(AuthenticateRequest authenticateRequest) {
        // Authenticate username and password
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authenticateRequest.getUserName(),authenticateRequest.getPassword())
        );
        var user = userRepository.findByUserName(authenticateRequest.getUserName()).orElseThrow();
        CustomUser customUser = new CustomUser(user);
        var jwt = jwtService.generateToken(customUser);
        var refreshToken = jwtService.generateRefreshToken(customUser);
        revokeAllUserTokens(user);
        saveUserToken(user,jwt);
        return AuthenticateResponse
                .builder()
                .accessToken(jwt)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwt) {
        var token = Token
                .builder()
                .user(user)
                .token(jwt)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens =tokenRepository.findAllValidTokenByUser(user.getId());
        if(validUserTokens.isEmpty()) return;
        validUserTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userName;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        refreshToken = authHeader.substring(7);
        userName = jwtService.extractUsername(refreshToken);
        if(userName != null) {
            var userDetails = this.userRepository.findByUserName(userName).orElseThrow();
            CustomUser customUser = new CustomUser(userDetails);
            // kiểm tra token có bị hết hạn hay bị thu hồi hay chưa
            if(jwtService.isTokenValid(refreshToken,customUser)) {
                var accessToken = jwtService.generateToken(customUser);
                revokeAllUserTokens(userDetails);
                saveUserToken(userDetails,accessToken);
                var authResponse = AuthenticateResponse
                        .builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(),authResponse);
            }
        }
    }
}