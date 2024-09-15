package com.gdyd.gdydcore.domain.mentoring;

import com.gdyd.gdydcore.domain.common.BaseTimeEntity;
import com.gdyd.gdydcore.domain.member.HighSchoolStudent;
import com.gdyd.gdydcore.domain.member.LikeList;
import com.gdyd.gdydcore.domain.report.Report;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@DynamicInsert
@Table(name = "high_school_student_question")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HighSchoolStudentQuestion extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "high_school_student_question_id")
    @EqualsAndHashCode.Include
    Long id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    String question;

    @Column(nullable = false)
    @ColumnDefault("0")
    Long answerCount;

    @Column(nullable = false)
    @ColumnDefault("0")
    Long likeCount;

    @Column(nullable = false)
    @ColumnDefault("0")
    Long reportCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    HighSchoolStudent highSchoolStudent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "highSchoolStudentQuestion", orphanRemoval = true)
    List<UniversityStudentAnswer> universityStudentAnswers = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "highSchoolStudentQuestion", orphanRemoval = true)
    List<LikeList> likeLists = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "highSchoolStudentQuestion", orphanRemoval = true)
    List<Report> reports = new ArrayList<>();

    @Builder
    public HighSchoolStudentQuestion(String title, String question, HighSchoolStudent highSchoolStudent) {
        this.title = title;
        this.question = question;
        this.highSchoolStudent = highSchoolStudent;
    }

    public void increaseAnswerCount() {
        this.answerCount++;
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
