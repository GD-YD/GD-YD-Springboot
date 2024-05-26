package com.gdyd.gdydapi.service.board;

import com.gdyd.gdydapi.request.board.SavePostReqeust;
import com.gdyd.gdydapi.request.board.UpdatePostRequest;
import com.gdyd.gdydapi.response.board.DeletePostResponse;
import com.gdyd.gdydapi.response.board.GetPostResponse;
import com.gdyd.gdydapi.response.board.SavePostResponse;
import com.gdyd.gdydapi.response.board.UpdatePostResponse;
import com.gdyd.gdydauth.utils.PrincipalUtil;
import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.domain.member.Member;
import com.gdyd.gdydcore.repository.member.MemberRepository;
import com.gdyd.gdydcore.service.board.PostService;
import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostCommandService {

    private final PostService postService;
    private final MemberRepository memberRepository;

    public SavePostResponse savePost(SavePostReqeust request) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member postMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));
        Post post = SavePostReqeust.toPost(request);
        post.UpdateMember(postMember);
        postService.savePost(post);
        return SavePostResponse.from(post);
    }

    public GetPostResponse getPostById(Long postId) {
        Post post = postService.getPostById(postId);
        return GetPostResponse.from(post);
    }

    public UpdatePostResponse updatePost(Long postId, UpdatePostRequest request) {
        Post post = UpdatePostRequest.toPost(request);
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Long postMemberId = postService.getPostById(postId).getMember().getId();
        if (memberId != postMemberId) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_MEMBER);
        }
        postService.updatePost(postId, post);
        return UpdatePostResponse.from(post);
    }

    public DeletePostResponse deletePost(Long postId) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Long postMemberId = postService.getPostById(postId).getMember().getId();
        if (memberId != postMemberId) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_MEMBER);
        }
        postService.deletePost(postId);
        return DeletePostResponse.from(postId);
    }
}
