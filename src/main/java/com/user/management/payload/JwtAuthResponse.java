package com.user.management.payload;

import com.user.management.entity.User;
import lombok.Data;

@Data
public class JwtAuthResponse {
    private String token;
    private User user;
}
