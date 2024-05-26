package com.gdyd.gdydcore.domain.member;

import com.gdyd.gdydcore.domain.mentoring.UniversityStudentAnswer;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

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
    Long enterYearUniversity;

    @Column(nullable = false)
    String universityMajor;

    @Column(nullable = false)
    String universityStudentId;

    @Column(nullable = false)
    String identificationUrl;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "universityStudent", orphanRemoval = true)
    List<UniversityStudentAnswer> universityStudentAnswers = new ArrayList<>();

    @Builder
    public UniversityStudent(String email, String password, String nickname, String name, String universityName, Grade universityGrade, Long enterYearUniversity, String universityMajor, String universityStudentId, String identificationUrl) {
        super(MemberType.UNIVERSITY_STUDENT, email, password, nickname, name);
        this.universityName = universityName;
        this.universityGrade = universityGrade;
        this.enterYearUniversity = enterYearUniversity;
        this.universityMajor = universityMajor;
        this.universityStudentId = universityStudentId;
        this.identificationUrl = identificationUrl;
    }
}
