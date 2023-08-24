package com.hmcuong.fooddelivery.auth.Register;

import com.hmcuong.fooddelivery.entity.User.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String fullName;
    private String userName;
    private String email;
    private String password;
    private Role role;
}
