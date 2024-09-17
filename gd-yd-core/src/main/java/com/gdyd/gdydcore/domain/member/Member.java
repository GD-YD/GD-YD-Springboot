package com.gdyd.gdydcore.domain.member;

import com.gdyd.gdydcore.domain.board.Comment;
import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.domain.common.BaseTimeEntity;
import com.gdyd.gdydcore.domain.report.Report;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "member")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    @EqualsAndHashCode.Include
    Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    MemberType type;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;

    @Column(nullable = false, unique = true)
    String nickname;

    @Column(nullable = false)
    String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", orphanRemoval = true)
    List<Post> posts = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", orphanRemoval = true)
    List<Comment> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", orphanRemoval = true)
    List<LikeList> likeLists = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", orphanRemoval = true)
    List<ScrapList> scrapLists = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reporter", orphanRemoval = true)
    List<Report> reportedList = new ArrayList<>();

    public Member(MemberType type, String email, String password, String nickname, String name) {
        this.type = type;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
