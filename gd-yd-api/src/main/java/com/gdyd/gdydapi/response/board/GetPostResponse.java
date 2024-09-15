package com.gdyd.gdydapi.response.board;

import com.gdyd.gdydapi.response.common.BoardMemberResponse;
import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.domain.board.PostMedia;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(description = "Post 상세 조회 응답 (댓글, 작성자 정보 포함)")
public record GetPostResponse (
        @Schema(description = "Post ID", example = "1")
        Long postId,

        @Schema(description = "작성자 정보")
        BoardMemberResponse member,

        @Schema(description = "Post 제목", example = "제 여친이 이상해요")
        String title,

        @Schema(description = "Post 내용", example = "자꾸 저를 무시해요")
        String content,

        @Schema(description = "좋아요 수", example = "4")
        Long likeCount,

        @Schema(description = "생성 시간", example = "2024-05-30 15:27:38.999973")
        String createdAt,

        @Schema(description = "최종 수정 시간", example = "2024-05-30 18:27:38.999973")
        String updatedAt,

        @Schema(description = "Post 댓글")
        List<GetCommentResponse> comments,

        @Schema(description = "Post 미디어 URL")
        List<String> postMediaUrls
) {
public static GetPostResponse from(Post post) {
        BoardMemberResponse memberResponse = BoardMemberResponse.from(post.getMember());
        List<GetCommentResponse> commentResponses = post.getComments().stream()
                .map(GetCommentResponse::from)
                .toList();
        List<String> postMediaUrls = post.getPostMedias().stream()
                .map(PostMedia::getUrl)
                .toList();

        return GetPostResponse.builder()
            .postId(post.getId())
            .member(memberResponse)
            .title(post.getTitle())
            .content(post.getContent())
            .likeCount(post.getLikeCount())
            .createdAt(post.getCreatedAt().toString())
            .updatedAt(post.getUpdatedAt().toString())
            .comments(commentResponses)
            .postMediaUrls(postMediaUrls)
            .build();
}
}
