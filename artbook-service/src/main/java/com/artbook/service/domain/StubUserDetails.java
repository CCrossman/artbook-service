package com.artbook.service.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static com.artbook.service.util.Preconditions.requireNonEmpty;

@EqualsAndHashCode
@ToString
public class StubUserDetails implements UserDetails {
    private final GrantedAuthority grantedAuthority;
    private final String email;

    public StubUserDetails(String email, String rawGrantedAuthority) {
        this.email = requireNonEmpty(email);
        this.grantedAuthority = () -> rawGrantedAuthority;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(grantedAuthority);
    }

    @Override
    public String getPassword() {
        return "STUBBED";
    }

    @Override
    public String getUsername() {
        return email;
    }
}
