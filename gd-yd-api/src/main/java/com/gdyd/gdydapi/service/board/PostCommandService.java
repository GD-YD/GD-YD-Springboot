package com.gdyd.gdydapi.service.board;

import com.gdyd.gdydapi.request.board.SavePostReqeust;
import com.gdyd.gdydapi.request.board.UpdatePostRequest;
import com.gdyd.gdydapi.response.board.DeletePostResponse;
import com.gdyd.gdydapi.response.board.SavePostResponse;
import com.gdyd.gdydapi.response.board.UpdatePostResponse;
import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.service.board.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostCommandService {

    private final PostService postService;

    public SavePostResponse savePost(SavePostReqeust request) {
        Post post = SavePostReqeust.toPost(request);
        postService.savePost(post);
        return SavePostResponse.from(post);
    }

    public UpdatePostResponse updatePost(Long postId, UpdatePostRequest request) {
        Post post = UpdatePostRequest.toPost(request);
        postService.updatePost(postId, post);
        return UpdatePostResponse.from(post);
    }

    public DeletePostResponse deletePost(Long postId) {
        postService.deletePost(postId);
        return DeletePostResponse.from(postId);
    }
}
