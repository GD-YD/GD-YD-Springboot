package com.gdyd.gdydcore.domain.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "verificaton_code")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VerificationCode {
    @Id
    @Column(name = "email")
    @EqualsAndHashCode.Include
    String email;

    @Column(nullable = false)
    String code;

    @Column(nullable = false)
    private LocalDateTime expireTime;

    public void updateCode(String code) {
        this.code = code;
    }

    public VerificationCode(String email, String code, LocalDateTime expireTime) {
        this.email = email;
        this.code = code;
        this.expireTime = expireTime;
    }

    public void updateExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public boolean isMatch(String email, String code) {
        return this.email.equals(email) && this.code.equals(code);
    }

    public boolean isExpired() {
        return expireTime.isBefore(LocalDateTime.now());
    }
}
