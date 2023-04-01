package com.example.healthiu.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.example.healthiu.entity.User;
import com.example.healthiu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken upAuth = (UsernamePasswordAuthenticationToken) authentication;
        final String login = (String) authentication.getPrincipal();

        final String password = (String) upAuth.getCredentials();

        final String storedPassword = userRepository.findById(login).map(User::getPassword)
                .orElseThrow(() -> new BadCredentialsException("illegal id or password"));
        final String role =  userRepository.findById(login).map(User::getRole)
                .orElseThrow(() -> new BadCredentialsException("illegal role"));

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(role));

        if (Objects.equals(password, "") || !Objects.equals(password, storedPassword)) {
            throw new BadCredentialsException("illegal id or password");
        }
        final UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                authentication.getPrincipal(), authentication.getCredentials(), authorities);
        result.setDetails(authentication.getDetails());

        return result;
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return true;
    }

}
