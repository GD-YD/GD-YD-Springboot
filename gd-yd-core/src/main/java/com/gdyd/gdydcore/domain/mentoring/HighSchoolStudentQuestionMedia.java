package com.gdyd.gdydcore.domain.mentoring;

import com.gdyd.gdydcore.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Entity
@Table(name = "high_school_student_question_media")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HighSchoolStudentQuestionMedia extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "high_school_student_question_media_id")
    @EqualsAndHashCode.Include
    Long id;

    @Column(nullable = false)
    String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "high_school_student_question_id", nullable = false)
    HighSchoolStudentQuestion highSchoolStudentQuestion;

    @Builder
    public HighSchoolStudentQuestionMedia(String url, HighSchoolStudentQuestion highSchoolStudentQuestion) {
        this.url = url;
        this.highSchoolStudentQuestion = highSchoolStudentQuestion;
    }
}
