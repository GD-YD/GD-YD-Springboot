package com.gdyd.gdydcore.domain.board;

import com.gdyd.gdydcore.domain.common.BaseTimeEntity;
import com.gdyd.gdydcore.domain.member.LikeList;
import com.gdyd.gdydcore.domain.member.Member;
import com.gdyd.gdydcore.domain.report.Report;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "comment")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    @EqualsAndHashCode.Include
    Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    String content;

    @Column(nullable = false)
    Long likeCount;

    @Column(nullable = false)
    Long reportCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    Post post;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment", orphanRemoval = true)
    List<LikeList> likeLists = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment", orphanRemoval = true)
    List<Report> reports = new ArrayList<>();

    @Builder
    public Comment(String content) {
        this.likeCount = 0L;
        this.reportCount = 0L;
        this.content = content;
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

    public void update(String content) {
        this.content = content;
    }

    public void updateMember(Member member) {
        this.member = member;
    }

    public void updatePost(Post post) {
        this.post = post;
    }
}
