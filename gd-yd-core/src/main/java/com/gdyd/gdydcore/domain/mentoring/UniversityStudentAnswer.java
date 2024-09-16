package com.gdyd.gdydcore.domain.mentoring;

import com.gdyd.gdydcore.domain.common.BaseTimeEntity;
import com.gdyd.gdydcore.domain.member.LikeList;
import com.gdyd.gdydcore.domain.member.UniversityStudent;
import com.gdyd.gdydcore.domain.report.Report;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    Long likeCount;

    @Column(nullable = false)
    @ColumnDefault("0")
    Long reportCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    UniversityStudent universityStudent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "high_school_student_question_id", nullable = false)
    HighSchoolStudentQuestion highSchoolStudentQuestion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "universityStudentAnswer", orphanRemoval = true)
    List<LikeList> likeLists = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "universityStudentAnswer", orphanRemoval = true)
    List<Report> reports = new ArrayList<>();

    @Builder
    public UniversityStudentAnswer(String answer, UniversityStudent universityStudent, HighSchoolStudentQuestion highSchoolStudentQuestion) {
        this.likeCount = 0L;
        this.answer = answer;
        this.universityStudent = universityStudent;
        this.highSchoolStudentQuestion = highSchoolStudentQuestion;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }

    public void increaseReportCount() {
        this.reportCount++;
    }
}
