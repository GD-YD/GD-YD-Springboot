package com.gdyd.gdydcore.domain.member;

import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.domain.common.BaseTimeEntity;
import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestion;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Entity
@Table(name = "scrap_list")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScrapList extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scrap_list_id")
    @EqualsAndHashCode.Include
    Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    ScrapType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "high_school_student_question_id")
    HighSchoolStudentQuestion highSchoolStudentQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    Post post;

    @Builder(builderMethodName = "highSchoolStudentQuestionScrapBuilder", buildMethodName = "highSchoolStudentQuestionScrapBuild")
    public ScrapList(Member member, HighSchoolStudentQuestion highSchoolStudentQuestion) {
        this.type = ScrapType.HIGH_SCHOOL_STUDENT_QUESTION;
        this.member = member;
        this.highSchoolStudentQuestion = highSchoolStudentQuestion;
    }

    @Builder(builderMethodName = "postScrapBuilder", buildMethodName = "postScrapBuild")
    public ScrapList(Member member, Post post) {
        this.type = ScrapType.POST;
        this.member = member;
        this.post = post;
    }
}
