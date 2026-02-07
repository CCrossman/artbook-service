package com.artbook.service.service;

import com.artbook.service.domain.StubUserDetails;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StubUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    // TODO
    private String stubbedPassword;

    @PostConstruct
    public void init() {
        this.stubbedPassword = passwordEncoder.encode("STUBBED");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("seejo.crux.x@gmail.com".equals(username)) {
            return new StubUserDetails(username, stubbedPassword, "registered-artist");
        }
        if ("seejo.crux@gmail.com".equals(username)) {
            return new StubUserDetails(username, stubbedPassword, "registered-viewer");
        }
        return new StubUserDetails(username, null, "guest");
    }
}
