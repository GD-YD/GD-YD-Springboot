package com.gdyd.gdydcore.domain.member;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Entity
@DiscriminatorValue("university_student")
@Table(name = "university_student")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UniversityStudent extends Member {

    @Column(nullable = false)
    String universityName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Grade universityGrade;

    @Column(nullable = false)
    String universityMajor;

    @Column(nullable = false)
    String universityStudentId;

    @Column(nullable = false)
    String identificationUrl;

    @Builder
    public UniversityStudent(String email, String password, String nickname, String name, String universityName, Grade universityGrade, String universityMajor, String universityStudentId, String identificationUrl) {
        super(email, password, nickname, name);
        this.universityName = universityName;
        this.universityGrade = universityGrade;
        this.universityMajor = universityMajor;
        this.universityStudentId = universityStudentId;
        this.identificationUrl = identificationUrl;
    }
}
