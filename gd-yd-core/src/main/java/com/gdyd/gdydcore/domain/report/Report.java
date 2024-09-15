package com.gdyd.gdydcore.domain.report;

import com.gdyd.gdydcore.domain.board.Comment;
import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.domain.member.LikeType;
import com.gdyd.gdydcore.domain.member.Member;
import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestion;
import com.gdyd.gdydcore.domain.mentoring.UniversityStudentAnswer;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Getter
@Entity
@Table(name = "report")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    @EqualsAndHashCode.Include
    Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    ReportType type;

    @Column(nullable = false)
    String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    Member reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "high_school_student_question_id")
    HighSchoolStudentQuestion highSchoolStudentQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_student_answer_id")
    UniversityStudentAnswer universityStudentAnswer;

    @Builder(builderMethodName = "postReportBuilder", buildMethodName = "postReportBuild")
    public Report(Member reporter, Post post, String content) {
        this.type = ReportType.POST;
        this.post = post;
        this.reporter = reporter;
        this.content = content;
    }

    @Builder(builderMethodName = "commentReportBuilder", buildMethodName = "commentReportBuild")
    public Report(Member reporter, Comment comment, String content) {
        this.type = ReportType.COMMENT;
        this.comment = comment;
        this.reporter = reporter;
        this.content = content;
    }

    @Builder(builderMethodName = "questionReportBuilder", buildMethodName = "questionReportBuild")
    public Report(Member reporter, HighSchoolStudentQuestion highSchoolStudentQuestion, String content) {
        this.type = ReportType.HIGH_SCHOOL_STUDENT_QUESTION;
        this.highSchoolStudentQuestion = highSchoolStudentQuestion;
        this.reporter = reporter;
        this.content = content;
    }

    @Builder(builderMethodName = "answerReportBuilder", buildMethodName = "answerReportBuild")
    public Report(Member reporter, UniversityStudentAnswer universityStudentAnswer, String content) {
        this.type = ReportType.UNIVERSITY_STUDENT_ANSWER;
        this.universityStudentAnswer = universityStudentAnswer;
        this.reporter = reporter;
        this.content = content;
    }
}
