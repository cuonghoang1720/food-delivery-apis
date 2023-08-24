package com.hmcuong.fooddelivery.error;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class ErrorMsg {
    private HttpStatus errCode;
    private String msg;

    public ErrorMsg(HttpStatus errorCode, String msg) {
        super();
        this.msg = msg;
        this.errCode = errorCode;
    }
}
