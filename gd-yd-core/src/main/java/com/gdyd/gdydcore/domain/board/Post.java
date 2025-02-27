package com.gdyd.gdydcore.domain.board;

import com.gdyd.gdydcore.domain.common.BaseTimeEntity;
import com.gdyd.gdydcore.domain.member.LikeList;
import com.gdyd.gdydcore.domain.member.Member;
import com.gdyd.gdydcore.domain.member.ScrapList;
import com.gdyd.gdydcore.domain.report.Report;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "post")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    @EqualsAndHashCode.Include
    Long id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    String content;

    @Column(nullable = false)
    Long viewCount;

    @Column(nullable = false)
    Long likeCount;

    @Column(nullable = false)
    Long reportCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    Member member;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", orphanRemoval = true)
    List<Comment> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", orphanRemoval = true)
    List<PostMedia> postMedias = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", orphanRemoval = true)
    List<LikeList> likeLists = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", orphanRemoval = true)
    List<ScrapList> scrapLists = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", orphanRemoval = true)
    List<Report> reports = new ArrayList<>();

    @Builder
    public Post(String title, String content) {
        this.likeCount = 0L;
        this.viewCount = 0L;
        this.reportCount = 0L;
        this.title = title;
        this.content = content;
    }

    public void increaseViewCount() {
        this.viewCount++;
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

    public void updateMember(Member member) {
        this.member = member;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updatePostMedias(List<PostMedia> postMedias) {
        this.postMedias = postMedias;
    }
}
