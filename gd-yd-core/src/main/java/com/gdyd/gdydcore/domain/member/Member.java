package com.gdyd.gdydcore.domain.member;

import com.gdyd.gdydcore.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Entity
@DiscriminatorColumn(name = "student_type")
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "member")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    @EqualsAndHashCode.Include
    Long id;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    String nickName;

    @Column(nullable = false)
    String name;

    public Member(String email, String password, String nickName, String name) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.name = name;
    }

    // TODO : Buisness Logic
}
