
package com.credit.app.core.config;

import lombok.Data;

@Data
public class AuthResp {
    private String token;
    private int statusCode;
    private String message;

    public AuthResp(String token) {
        this.token = token;
    }

    public AuthResp(String token, int statusCode, String message) {
        this.token = token;
        this.statusCode = statusCode;
        this.message = message;
    }
}
