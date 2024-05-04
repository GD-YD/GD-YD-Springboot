package com.gdyd.gdydauth.jwt;

public enum JwtValidationType {
    VALID_JWT,
    INVALID_JWT_SIGNATURE,
    INVALID_JWT,
    EXPIRED_JWT,
    UNSUPPORTED_JWT,
    EMPTY_JWT
}
