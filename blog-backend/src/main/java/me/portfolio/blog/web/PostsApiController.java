package me.portfolio.blog.web;

import lombok.RequiredArgsConstructor;
import me.portfolio.blog.config.auth.LoginUser;
import me.portfolio.blog.config.auth.dto.SessionUser;
import me.portfolio.blog.domain.user.User;
import me.portfolio.blog.service.PostsService;
import me.portfolio.blog.web.dto.posts.PostsListResponseDto;
import me.portfolio.blog.web.dto.posts.PostsResponseDto;
import me.portfolio.blog.web.dto.posts.PostsSaveRequestDto;
import me.portfolio.blog.web.dto.posts.PostsUpdateRequestDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin(origins = {"http://ec2-13-125-108-168.ap-northeast-2.compute.amazonaws.com", "http://localhost:8000", "http://localhost:3000"}, allowCredentials = "true")
@RequestMapping("/api/v2")
@RestController
public class PostsApiController {

    private final PostsService postsService;

    //포스트 리스트 조회
    @GetMapping("/posts")
    public List<PostsListResponseDto> getPostList() throws Exception {
        return postsService.getPostList();
    }

    @GetMapping("/posts/search")
    public List<PostsListResponseDto> getSearchedList(@RequestParam("search") String title) throws Exception {
        return postsService.getListSearchTitle(title);
    }

    //포스트 등록
    @PostMapping("/posts")
    public Long addPost(@LoginUser SessionUser sessionUser,
                        @RequestParam(value = "image", required = false) MultipartFile image,
                        @RequestParam("title") String title,
                        @RequestParam("content") String content) throws Exception {
        return postsService.addPost(sessionUser, image, title, content);
    }

    //포스트 조회
    @GetMapping("/posts/{postId}")
    public PostsResponseDto getPost(@PathVariable Long postId) throws Exception {
        return postsService.getPost(postId);
    }

    //포스트 수정
    @PutMapping("/posts/{postId}")
    public Long updatePost(@PathVariable Long postId, @RequestBody PostsUpdateRequestDto requestDto) throws Exception {
        return postsService.updatePost(postId, requestDto);
    }

    //포스트 삭제
    @DeleteMapping("/posts/{postId}")
    public String deletePost(@PathVariable Long postId) throws Exception {
        postsService.deletePost(postId);
        return postId + "번 포스트 삭제 완료";
    }


}
