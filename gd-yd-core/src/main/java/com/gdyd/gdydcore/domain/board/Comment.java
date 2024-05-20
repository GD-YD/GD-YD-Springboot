package com.gdyd.gdydcore.domain.board;

import com.gdyd.gdydcore.domain.common.BaseTimeEntity;
import com.gdyd.gdydcore.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Entity
@DynamicInsert
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
    @ColumnDefault("0")
    Long likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    Post post;

    @Builder
    public Comment(String content) {
        this.content = content;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void update(String content) {
        this.content = content;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
