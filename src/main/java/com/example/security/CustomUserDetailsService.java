package com.example.security;

import com.example.entity.Operator;
import com.example.repository.OperatorRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final OperatorRepository operatorRepository;

    public CustomUserDetailsService(OperatorRepository operatorRepository) {
        this.operatorRepository = operatorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Operator operator = operatorRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return new User(
                operator.getUsername(),
                operator.getPassword(),
                Collections.singleton(
                        new SimpleGrantedAuthority(operator.getRole())
                )
        );
    }
}