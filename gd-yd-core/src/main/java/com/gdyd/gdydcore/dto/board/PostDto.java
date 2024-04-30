package com.gdyd.gdydcore.dto.board;

import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.domain.member.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private Long viewCount;
    private Long likeCount;
    private Long memberId;

    public Post toDomain() {
        Post post = Post.builder()
                .title(title)
                .content(content)
                .build();

        return post;
    }

    @Builder
    public PostDto(Long id, String title, String content, Long viewCount, Long likeCount, Long memberId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.memberId = memberId;
    }
}