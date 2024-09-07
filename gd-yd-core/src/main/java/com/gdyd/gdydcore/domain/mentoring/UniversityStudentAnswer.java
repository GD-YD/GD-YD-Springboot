package com.gdyd.gdydcore.domain.mentoring;

import com.gdyd.gdydcore.domain.common.BaseTimeEntity;
import com.gdyd.gdydcore.domain.member.LikeList;
import com.gdyd.gdydcore.domain.member.UniversityStudent;
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
    @ColumnDefault("0")
    Long likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    UniversityStudent universityStudent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "high_school_student_question_id", nullable = false)
    HighSchoolStudentQuestion highSchoolStudentQuestion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "universityStudentAnswer", orphanRemoval = true)
    List<LikeList> likeLists = new ArrayList<>();

    @Builder
    public UniversityStudentAnswer(String answer, UniversityStudent universityStudent, HighSchoolStudentQuestion highSchoolStudentQuestion) {
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
}
