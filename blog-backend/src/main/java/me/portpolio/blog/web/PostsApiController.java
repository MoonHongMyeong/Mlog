package me.portpolio.blog.web;

import lombok.RequiredArgsConstructor;
import me.portpolio.blog.domain.posts.Posts;
import me.portpolio.blog.service.PostsService;
import me.portpolio.blog.web.dto.posts.PostsResponseDto;
import me.portpolio.blog.web.dto.posts.PostsSaveRequestDto;
import me.portpolio.blog.web.dto.posts.PostsUpdateRequestDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class PostsApiController {

    private final PostsService postsService;

    //포스트 리스트 조회
    @GetMapping("/posts")
    public List<Posts> getPostList()throws Exception{
        return postsService.getPostList();
    }

    //포스트 등록
    @PostMapping("/posts")
    public Long addPost(@RequestBody PostsSaveRequestDto requestDto) throws Exception{
        return postsService.addPost(requestDto);
    }

    //포스트 조회
    @GetMapping("/posts/{postId}")
    public PostsResponseDto getPost(@PathVariable Long postId) throws Exception{
        return postsService.getPost(postId);
    }

    //포스트 수정
    @PutMapping("/posts/{postId}")
    public Long updatePost(@PathVariable Long postId, @RequestBody PostsUpdateRequestDto requestDto) throws Exception{
        return postsService.updatePost(postId, requestDto);
    }

    //포스트 삭제
    @DeleteMapping("/posts/{postId}")
    public Long deletePost(@PathVariable Long postId) throws Exception{
        postsService.deletePost(postId);
        return postId;
    }


}
