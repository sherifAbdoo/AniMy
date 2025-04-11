package com.AniMy.controller;

import com.AniMy.services.RegistrationService;
import com.AniMy.utils.registerRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registration")
@AllArgsConstructor
public class Register {

    private RegistrationService registrationService;

    @GetMapping
    public String hello(){
        return "Hello from Register";
    }

    @PostMapping
    public String registerUser(@RequestBody registerRequest request) {
        return registrationService.register(request);
    }

}
