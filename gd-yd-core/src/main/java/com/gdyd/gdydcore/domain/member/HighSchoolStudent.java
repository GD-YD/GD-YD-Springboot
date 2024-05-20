package com.gdyd.gdydcore.domain.member;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Entity
@DiscriminatorValue("high_school_student")
@Table(name = "high_school_student")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HighSchoolStudent extends Member {

    @Column(nullable = false)
    String highSchoolName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Grade highSchoolGrade;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    HighSchoolMajor highSchoolMajor;

    @Column(nullable = false)
    String highSchoolStudentId;

    @Column(nullable = false)
    String identificationUrl;

    @Builder
    public HighSchoolStudent(String email, String password, String nickname, String name, String highSchoolName, Grade highSchoolGrade, HighSchoolMajor highSchoolMajor, String highSchoolStudentId, String identificationUrl) {
        super(MemberType.HIGH_SCHOOL_STUDENT, email, password, nickname, name);
        this.highSchoolName = highSchoolName;
        this.highSchoolGrade = highSchoolGrade;
        this.highSchoolMajor = highSchoolMajor;
        this.highSchoolStudentId = highSchoolStudentId;
        this.identificationUrl = identificationUrl;
    }

}
