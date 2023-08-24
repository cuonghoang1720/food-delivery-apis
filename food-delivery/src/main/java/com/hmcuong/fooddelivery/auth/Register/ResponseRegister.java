package com.hmcuong.fooddelivery.auth.Register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseRegister {
    private RegisterRequest registerRequest;
    private String msg;
}
