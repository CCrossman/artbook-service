package com.artbook.service.service;

import com.artbook.service.domain.StubUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StubUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("seejo.crux.x@gmail.com".equals(username)) {
            return new StubUserDetails(username, "registered-artist");
        }
        if ("seejo.crux@gmail.com".equals(username)) {
            return new StubUserDetails(username, "registered-viewer");
        }
        return new StubUserDetails(username, "guest");
    }
}
