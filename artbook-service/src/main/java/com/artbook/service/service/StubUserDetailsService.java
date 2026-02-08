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
            return new StubUserDetails(username, stubbedPassword, getPermissionsForRegisteredArtist());
        }
        if ("seejo.crux@gmail.com".equals(username)) {
            return new StubUserDetails(username, stubbedPassword, getPermissionsForRegisteredViewer());
        }
        throw new UsernameNotFoundException("User not found: '" + username + "'");
    }

    private String[] getPermissionsForRegisteredViewer() {
        return new String[] {
            "view_full_image",
            "view_gallery",
            "view_registered_user",
            "use_image_like",
            "use_follow",
            "use_image_report",
        };
    }

    private static String[] getPermissionsForRegisteredArtist() {
        return new String[] {
            "view_full_image",
            "view_gallery",
            "view_registered_user",
            "use_image_like",
            "use_image_upload",
            "use_follow",
            "use_image_report",
        };
    }
}
