package com.gdyd.gdydauth.jwt;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token {

    private String value;
    private Date expiredTime;

    @Builder
    public Token(String value, Date expiredTime) {
        this.value = value;
        this.expiredTime = expiredTime;
    }
}