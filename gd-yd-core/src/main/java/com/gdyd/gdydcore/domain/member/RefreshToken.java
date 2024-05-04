package com.gdyd.gdydcore.domain.member;

import com.gdyd.gdydcore.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Entity
@Table(name = "refresh_token")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    @EqualsAndHashCode.Include
    Long id;

    @Column(name = "member_id")
    Long memberId;

    @Column(length = 512)
    String token;

    Date expiredAt;

    @Builder
    public RefreshToken(Long memberId, String token, Date expiredAt) {
        this.memberId = memberId;
        this.token = token;
        this.expiredAt = expiredAt;
    }

    public void updateToken(String token) {
        this.token = token;
    }

    public void updateExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }
}
