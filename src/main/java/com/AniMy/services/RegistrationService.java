package com.AniMy.services;

import com.AniMy.utils.registerRequest;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    public String register(registerRequest request) {
        return "Works";
    }
}
