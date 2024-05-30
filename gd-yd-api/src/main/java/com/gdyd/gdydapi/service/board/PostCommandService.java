package com.gdyd.gdydapi.service.board;

import com.gdyd.gdydapi.request.board.SavePostReqeust;
import com.gdyd.gdydapi.request.board.UpdatePostRequest;
import com.gdyd.gdydapi.response.board.*;
import com.gdyd.gdydauth.utils.PrincipalUtil;
import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.domain.member.Member;
import com.gdyd.gdydcore.service.board.PostService;
import com.gdyd.gdydcore.service.member.MemberService;
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

    public SavePostResponse savePost(SavePostReqeust request) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member member = memberService.getMemberById(memberId);
        Post post = SavePostReqeust.toPost(request);
        post.updateMember(member);
        postService.savePost(post);
        return SavePostResponse.from(post);
    }

    public GetAllPostResponse getAllPost() {
        List<Post> posts = postService.getAllPost();
        return GetAllPostResponse.from(posts);
    }

    public GetPostResponse getPostById(Long postId) {
        Post post = postService.getPostById(postId);
        return GetPostResponse.from(post);
    }

    public UpdatePostResponse updatePost(Long postId, UpdatePostRequest request) {
        Post post = UpdatePostRequest.toPost(request);
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        postService.getPostByIdAndMemberId(postId, memberId);
        postService.updatePost(postId, post);
        return UpdatePostResponse.from(post);
    }

    public DeletePostResponse deletePost(Long postId) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        postService.getPostByIdAndMemberId(postId, memberId);
        postService.deletePost(postId);
        return DeletePostResponse.from(postId);
    }
}
