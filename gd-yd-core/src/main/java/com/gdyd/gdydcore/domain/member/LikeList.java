package com.gdyd.gdydcore.domain.member;

import com.gdyd.gdydcore.domain.board.Comment;
import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.domain.common.BaseTimeEntity;
import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestion;
import com.gdyd.gdydcore.domain.mentoring.UniversityStudentAnswer;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Entity
@Table(name = "like_list")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeList extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_list_id")
    @EqualsAndHashCode.Include
    Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    LikeType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "high_school_student_question_id")
    HighSchoolStudentQuestion highSchoolStudentQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_student_answer_id")
    UniversityStudentAnswer universityStudentAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    Comment comment;

    @Builder(builderMethodName = "highSchoolStudentQuestionLikeBuilder", buildMethodName = "highSchoolStudentQuestionLikeBuild")
    public LikeList(Member member, HighSchoolStudentQuestion highSchoolStudentQuestion) {
        this.type = LikeType.HIGH_SCHOOL_STUDENT_QUESTION;
        this.member = member;
        this.highSchoolStudentQuestion = highSchoolStudentQuestion;
    }

    @Builder(builderMethodName = "universityStudentAnswerLikeBuilder", buildMethodName = "universityStudentAnswerLikeBuild")
    public LikeList(Member member, UniversityStudentAnswer universityStudentAnswer) {
        this.type = LikeType.UNIVERSITY_STUDENT_ANSWER;
        this.member = member;
        this.universityStudentAnswer = universityStudentAnswer;
    }

    @Builder(builderMethodName = "postLikeBuilder", buildMethodName = "postLikeBuild")
    public LikeList(Member member, Post post) {
        this.type = LikeType.POST;
        this.member = member;
        this.post = post;
    }

    @Builder(builderMethodName = "commentLikeBuilder", buildMethodName = "commentLikeBuild")
    public LikeList(Member member, Comment comment) {
        this.type = LikeType.COMMENT;
        this.member = member;
        this.comment = comment;
    }
}
