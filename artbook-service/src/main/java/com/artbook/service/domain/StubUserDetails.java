package com.artbook.service.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static com.artbook.service.util.Preconditions.requireNonEmpty;

@EqualsAndHashCode
@ToString
public class StubUserDetails implements UserDetails {
    private final List<GrantedAuthority> grantedAuthorities;
    private final String email, password;

    public StubUserDetails(String email, String password, String... rawGrantedAuthorities) {
        this.email = requireNonEmpty(email);
        this.password = requireNonEmpty(password);
        this.grantedAuthorities = Stream.of(rawGrantedAuthorities)
            .map(s -> (GrantedAuthority) () -> s)
            .toList();      // 'toList' returns unmodifiable list
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
