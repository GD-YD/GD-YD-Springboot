package com.gdyd.gdydauth.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class UserAuthentication extends UsernamePasswordAuthenticationToken {
    public UserAuthentication(Long principal, String credentials) {
        super(principal, credentials);
    }

    public UserAuthentication(Long principal, String credentials, List<GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
