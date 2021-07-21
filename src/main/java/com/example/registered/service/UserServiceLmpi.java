package com.example.registered.service;

import com.example.registered.model.Role;
import com.example.registered.model.User;
import com.example.registered.repository.UserRepository;
import com.example.registered.web.Dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceLmpi implements UserService{
    private final UserRepository userRepository;


    public UserServiceLmpi(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User save(UserRegistrationDto userRegistrationDto) {
       User user=new User(userRegistrationDto.getFirstName(),
               userRegistrationDto.getLastName(),
               userRegistrationDto.getEmail(),
              passwordEncoder.encode(userRegistrationDto.getPassword()), Arrays.asList(new Role("ROLE_USER")));
       return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(username);
        if (user==null){
            throw new UsernameNotFoundException("Invalid username or password ");
        }
        return new  org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),grantedAuthorities(user.getRoles()));

    }

    private Collection<? extends GrantedAuthority> grantedAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

    }
}
