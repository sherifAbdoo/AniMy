package com.AniMy.services;

import com.AniMy.models.User;
import com.AniMy.repository.UserRepository;
import com.AniMy.utils.ExceptionMsg;
import com.AniMy.utils.RegistrationMsg;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServices implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException
                        (String.format(ExceptionMsg.USER_NOT_FOUND, username)));
    }

    public ResponseEntity<String> signUpUser(User user){


        boolean usernameExits = userRepository.findByUsername(user.getUsername()).isPresent();
        if(usernameExits){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(RegistrationMsg.USER_ALREADY_EXISTS);
        }
        boolean emailExits = userRepository.findByEmail(user.getEmail()).isPresent();
        if(emailExits){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(RegistrationMsg.EMAIL_ALREADY_EXISTS);
        }

        userRepository.save(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(RegistrationMsg.USER_REGISTRATION_SUCCESS);
    }
}
