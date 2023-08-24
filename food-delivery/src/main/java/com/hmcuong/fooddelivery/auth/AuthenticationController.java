package com.hmcuong.fooddelivery.auth;

import com.hmcuong.fooddelivery.auth.Register.ResponseRegister;
import com.hmcuong.fooddelivery.auth.Register.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @GetMapping("/")
    public ResponseEntity<Object> sayHello() {
        return new ResponseEntity<>("Hello from API", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest registerRequest){
        ResponseRegister responseRegister = authenticationService.register(registerRequest);
        if(responseRegister.getMsg().equals("username already exists")
                || responseRegister.getMsg().equals("email already exists"))
        {
            return new ResponseEntity<>(responseRegister,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseRegister,HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticateRequest authenticateRequest) {
        AuthenticateResponse authenticateResponse = authenticationService.authenticate(authenticateRequest);
        return new ResponseEntity<>(authenticateResponse,HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request,response);
    }

}
