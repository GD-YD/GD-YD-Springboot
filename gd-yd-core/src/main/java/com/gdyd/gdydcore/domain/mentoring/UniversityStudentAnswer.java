package com.gdyd.gdydcore.domain.mentoring;

import com.gdyd.gdydcore.domain.common.BaseTimeEntity;
import com.gdyd.gdydcore.domain.member.UniversityStudent;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Entity
@Table(name = "university_student_answer")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UniversityStudentAnswer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_student_answer_id")
    @EqualsAndHashCode.Include
    Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    UniversityStudent universityStudent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "high_school_student_question_id", nullable = false)
    HighSchoolStudentQuestion highSchoolStudentQuestion;
}
