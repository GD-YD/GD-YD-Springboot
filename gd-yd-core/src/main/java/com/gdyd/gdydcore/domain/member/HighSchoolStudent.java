package com.gdyd.gdydcore.domain.member;

import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestion;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

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
    Long enterYearHighSchool;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    HighSchoolMajor highSchoolMajor;

    @Column(nullable = false)
    String highSchoolStudentId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "highSchoolStudent", orphanRemoval = true)
    List<HighSchoolStudentQuestion> highSchoolStudentQuestions = new ArrayList<>();

    @Builder
    public HighSchoolStudent(String email, String password, String nickname, String name, String highSchoolName, Grade highSchoolGrade, Long enterYearHighSchool, HighSchoolMajor highSchoolMajor, String highSchoolStudentId) {
        super(MemberType.HIGH_SCHOOL_STUDENT, email, password, nickname, name);
        this.highSchoolName = highSchoolName;
        this.highSchoolGrade = highSchoolGrade;
        this.enterYearHighSchool = enterYearHighSchool;
        this.highSchoolMajor = highSchoolMajor;
        this.highSchoolStudentId = highSchoolStudentId;
    }

}
