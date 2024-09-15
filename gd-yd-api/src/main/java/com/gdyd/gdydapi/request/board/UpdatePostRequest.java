package com.gdyd.gdydapi.request.board;

import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.domain.board.PostMedia;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Post 수정 요청")
public record UpdatePostRequest (
    @Schema(description = "Post 제목", example = "제 여친이 이상해요")
    String title,

    @Schema(description = "Post 내용", example = "자꾸 저를 무시해요")
    String content,

    @Schema(description = "Post 미디어 URL")
    List<String> postMediaUrls
) {
    public static Post toPost(UpdatePostRequest request) {
        return Post.builder()
                .title(request.title())
                .content(request.content())
                .build();
    }

    public static List<PostMedia> toPostMedia(UpdatePostRequest request, Post post) {
        return request.postMediaUrls().stream()
                .map(url -> PostMedia.builder()
                        .url(url)
                        .post(post)
                        .build())
                .toList();
    }
}
