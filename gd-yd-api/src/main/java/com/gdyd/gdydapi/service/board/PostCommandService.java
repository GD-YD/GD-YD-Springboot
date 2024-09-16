package com.gdyd.gdydapi.service.board;

import com.gdyd.gdydapi.request.board.SavePostReqeust;
import com.gdyd.gdydapi.request.board.UpdatePostRequest;
import com.gdyd.gdydapi.response.board.DeletePostResponse;
import com.gdyd.gdydapi.response.board.SavePostResponse;
import com.gdyd.gdydapi.response.board.UpdatePostResponse;
import com.gdyd.gdydapi.response.common.LikeListResponse;
import com.gdyd.gdydapi.response.common.ScrapListResponse;
import com.gdyd.gdydauth.utils.PrincipalUtil;
import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.domain.board.PostMedia;
import com.gdyd.gdydcore.domain.member.LikeList;
import com.gdyd.gdydcore.domain.member.Member;
import com.gdyd.gdydcore.domain.member.ScrapList;
import com.gdyd.gdydcore.service.board.PostMediaService;
import com.gdyd.gdydcore.service.board.PostService;
import com.gdyd.gdydcore.service.member.LikeListService;
import com.gdyd.gdydcore.service.member.MemberService;
import com.gdyd.gdydcore.service.member.ScrapListService;
import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostCommandService {

    private final PostService postService;
    private final MemberService memberService;
    private final LikeListService likeListService;
    private final PostMediaService postMediaService;
    private final ScrapListService scrapListService;

    public SavePostResponse savePost(SavePostReqeust request) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member member = memberService.getMemberById(memberId);

        Post post = SavePostReqeust.toPost(request);
        post.updateMember(member);
        postService.savePost(post);

        if (request.postMediaUrls() != null) {
            List<PostMedia> postMedias = SavePostReqeust.toPostMedia(request, post);
            postMediaService.saveAllPostMedia(postMedias);
            post.updatePostMedias(postMedias);
        }
        return SavePostResponse.from(post);
    }

    public UpdatePostResponse updatePost(Long postId, UpdatePostRequest request) {
        Post post = UpdatePostRequest.toPost(request);
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Post savedPost = postService.getPostByIdAndMemberId(postId, memberId);
        savedPost.update(post.getTitle(), post.getContent());

        List<PostMedia> savedPostMedias = UpdatePostRequest.toPostMedia(request, savedPost);
        postMediaService.deleteAllPostMedia(savedPost.getPostMedias());
        postMediaService.saveAllPostMedia(savedPostMedias);
        return UpdatePostResponse.from(savedPost);
    }

    public DeletePostResponse deletePost(Long postId) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        postService.getPostByIdAndMemberId(postId, memberId);
        postService.deletePost(postId);
        return DeletePostResponse.from(postId);
    }

    /**
     * 게시글 좋아요
     */
    public LikeListResponse likePost(Long postId) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member member = memberService.getMemberById(memberId);
        Post post = postService.getPostById(postId);

        if (likeListService.existsByMemberIdAndPostId(memberId, postId)) {
            throw new BusinessException(ErrorCode.AREADY_LIKED);
        }

        post.increaseLikeCount();
        LikeList likeList = LikeList.postLikeBuilder()
                .member(member)
                .post(post)
                .postLikeBuild();
        likeListService.save(likeList);
        return LikeListResponse.from(likeList);
    }

    /**
     * 게시글 좋아요 취소
     */
    public LikeListResponse dislikePost(Long postId) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member member = memberService.getMemberById(memberId);
        Post post = postService.getPostById(postId);

        if (!likeListService.existsByMemberIdAndPostId(memberId, postId)) {
            throw new BusinessException(ErrorCode.AREADY_UNLIKED);
        }

        post.decreaseLikeCount();
        LikeList likeList = member.getLikeLists().stream()
                .filter(like -> like.getPost().getId().equals(postId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.AREADY_UNLIKED));
        likeListService.delete(likeList);
        return LikeListResponse.from(likeList);
    }

    /**
     * 게시글 스크랩
     */
    public ScrapListResponse scrapPost(Long postId) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member member = memberService.getMemberById(memberId);
        Post post = postService.getPostById(postId);

        if (scrapListService.existsByMemberIdAndPostId(memberId, postId)) {
            throw new BusinessException(ErrorCode.ALREADY_SCRAPED);
        }

        ScrapList scrapList = ScrapList.postScrapBuilder()
                .member(member)
                .post(post)
                .postScrapBuild();
        scrapListService.save(scrapList);
        return ScrapListResponse.from(scrapList);
    }

    /**
     * 게시글 스크랩 취소
     */
    public ScrapListResponse unscrapPost(Long postId) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member member = memberService.getMemberById(memberId);

        if (!scrapListService.existsByMemberIdAndPostId(memberId, postId)) {
            throw new BusinessException(ErrorCode.ALREADY_UNSCRAPED);
        }

        ScrapList scrapList = member.getScrapLists().stream()
                .filter(scrap -> scrap.getPost().getId().equals(postId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.ALREADY_UNSCRAPED));
        scrapListService.delete(scrapList);
        return ScrapListResponse.from(scrapList);
    }
}
