package org.project.bilryozo.global.security.login.service;

import lombok.RequiredArgsConstructor;
import org.project.bilryozo.domain.users.domain.Users;
import org.project.bilryozo.domain.users.repository.UsersRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + username));

        return User.builder()
                .username(users.getUsername())
                .password(users.getPassword())
                .roles(users.getRole().name())
                .build();
    }
}