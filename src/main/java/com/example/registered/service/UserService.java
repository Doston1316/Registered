package com.example.registered.service;

import com.example.registered.model.User;
import com.example.registered.web.Dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User save(UserRegistrationDto userRegistrationDto);
}
