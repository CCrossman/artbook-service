package com.artbook.service.service;

import com.artbook.service.domain.StubUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StubUserDetailsService implements UserDetailsService {

    @Value("${STUBBED_BCRYPT_PASSWORD}")
    private String stubbedPassword;

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
