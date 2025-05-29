package com.example.demo.services;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.BCryptUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptUtils bCryptUtils;

    public JpaUserDetailsService(UserRepository userRepository, BCryptUtils bCryptUtils) {
        this.userRepository = userRepository;
        this.bCryptUtils = bCryptUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("DEFAULT")
                .build();
    }
}
