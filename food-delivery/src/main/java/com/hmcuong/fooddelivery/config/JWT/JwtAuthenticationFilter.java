package com.hmcuong.fooddelivery.config.JWT;

import com.hmcuong.fooddelivery.config.CustomUserDetailsService;
import com.hmcuong.fooddelivery.entity.Token.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException
    {
        if(request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request,response);
            return;
        }
        System.out.println("request.getServletPath(): " + request.getServletPath());
        final String authHeader = request.getHeader("Authorization");
        System.out.println("authHeader: " + authHeader);
        final String jwt;
        final String userName;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            System.out.println("DEBAG");
            filterChain.doFilter(request,response);
            return;
        }
        jwt = authHeader.substring(7);
        System.out.println("Chuoi JWT:"+jwt);
        userName = jwtService.extractUsername(jwt);
        System.out.println("Username : "+userName);
        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(userName);
            // kiểm tra token có bị hết hạn hay bị thu hồi hay chưa
            var isTokenValid = tokenRepository.findByToken(jwt).map(t -> !t.isExpired() && !t.isRevoked()).orElse(false);
            if( isTokenValid && jwtService.isTokenValid(jwt,userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
