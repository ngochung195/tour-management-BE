package com.example.tour_management.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncodePasswordTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encoded = encoder.encode("123456");
        System.out.println(encoded);
    }
}
