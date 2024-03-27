package com.gdyd.gdydcore.domain.member;

import com.gdyd.gdydcore.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Entity
@Table(name = "profile")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    @EqualsAndHashCode.Include
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String school;

    @Column(nullable = false)
    String major;

    @Column(nullable = false)
    String grade;

    @Column(nullable = false)
    String identificationUrl;

    @Column(nullable = false)
    String StudentId;

    @Builder
    public Profile(String name, String school, String major, String grade, String identificationUrl, String StudentId) {
        this.name = name;
        this.school = school;
        this.major = major;
        this.grade = grade;
        this.identificationUrl = identificationUrl;
        this.StudentId = StudentId;
    }
}
