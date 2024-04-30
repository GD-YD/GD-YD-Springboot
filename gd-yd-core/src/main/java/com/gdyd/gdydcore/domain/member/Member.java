package com.gdyd.gdydcore.domain.member;

import com.gdyd.gdydcore.domain.board.Comment;
import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@DiscriminatorColumn(name = "student_type")
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

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;

    @Column(nullable = false, unique = true)
    String nickName;

    @Column(nullable = false)
    String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", orphanRemoval = true)
    List<Post> posts = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", orphanRemoval = true)
    List<Comment> comments = new ArrayList<>();

    public Member(String email, String password, String nickName, String name) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.name = name;
    }
}
