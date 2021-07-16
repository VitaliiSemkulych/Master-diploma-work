package com.example.demo.encoder;

import org.springframework.security.crypto.password.PasswordEncoder;

//own implementation for password encoder
public class BasePasswordEnconder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return charSequence.toString().equals(s);
    }
}
