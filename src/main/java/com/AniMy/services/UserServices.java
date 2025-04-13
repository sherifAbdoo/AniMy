package com.AniMy.services;

import com.AniMy.models.EmailConfirmationToken;
import com.AniMy.models.User;
import com.AniMy.repository.UserRepository;
import com.AniMy.utils.ExceptionMsg;
import com.AniMy.utils.RegistrationMsg;
import com.AniMy.utils.ApiResponse;
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
    private final EmailConfirmationService emailConfirmationService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException
                        (String.format(ExceptionMsg.USER_NOT_FOUND, username)));
    }

    public ResponseEntity<ApiResponse<String>> signUpUser(User user){

        boolean usernameExists = userRepository.findByUsername(user.getUsername()).isPresent();
        if (usernameExists) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(
                            RegistrationMsg.USER_ALREADY_EXISTS,
                            null));
        }

        boolean emailExists = userRepository.findByEmail(user.getEmail()).isPresent();
        if (emailExists) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(
                            RegistrationMsg.EMAIL_ALREADY_EXISTS,
                            null));
        }
        userRepository.save(user);

        EmailConfirmationToken emailConfirmationToken = emailConfirmationService.createToken(user);
        emailConfirmationService.saveConfirmationToken(emailConfirmationToken);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        RegistrationMsg.USER_REGISTRATION_SUCCESS,
                        emailConfirmationToken.getToken()
                ));
    }

}
