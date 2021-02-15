package me.portfolio.blog.web;

import lombok.RequiredArgsConstructor;
import me.portfolio.blog.config.auth.LoginUser;
import me.portfolio.blog.config.auth.dto.SessionUser;
import me.portfolio.blog.service.CategoriesService;
import me.portfolio.blog.service.PostsService;
import me.portfolio.blog.web.dto.categories.CategoriesResponseDto;
import me.portfolio.blog.web.dto.posts.PostsListResponseDto;
import me.portfolio.blog.web.dto.posts.PostsResponseDto;
import me.portfolio.blog.web.dto.posts.PostsUpdateRequestDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin(origins = {"http://ec2-13-125-108-168.ap-northeast-2.compute.amazonaws.com", "http://localhost:8000", "http://localhost:3000"}, allowCredentials = "true")
@RequestMapping("/api/v2")
@RestController
public class PostsApiController {

    private final PostsService postsService;
    private final CategoriesService categoriesService;

    //포스트 리스트 조회
    @GetMapping("/posts")
    public List<PostsListResponseDto> getPostList() throws Exception {
        return postsService.getPostList();
    }

    @GetMapping("/posts/search")
    public List<PostsListResponseDto> getSearchedList(@RequestParam("search") String title) throws Exception {
        return postsService.getListSearchTitle(title);
    }

    //포스트 등록 폼으로 이동 시 현재 로그인한 유저의 카테고리 정보를 불러옴
    @GetMapping("/posts/form")
    public List<CategoriesResponseDto> getUserCategoryList(@LoginUser SessionUser sessionUser) throws Exception {
        return categoriesService.getUserCategories(sessionUser);
    }

    //포스트 등록
    @PostMapping("/posts")
    public Long addPost(@LoginUser SessionUser sessionUser,
                        @RequestParam(value = "image", required = false) MultipartFile image,
                        @RequestParam("title") String title,
                        @RequestParam("content") String content,
                        @RequestParam("category") String categoryName) throws Exception {
        return postsService.addPost(sessionUser, image, title, content, categoryName);
    }

    //포스트 조회
    @GetMapping("/posts/{postId}")
    public PostsResponseDto getPost(@PathVariable(name = "postId") Long postId) throws Exception {
        return postsService.getPost(postId);
    }

    //포스트 수정
    @PutMapping("/posts/{postId}")
    public Long updatePost(@PathVariable(name = "postId") Long postId, @RequestBody PostsUpdateRequestDto requestDto) throws Exception {
        return postsService.updatePost(postId, requestDto);
    }

    //포스트 삭제
    @DeleteMapping("/posts/{postId}")
    public String deletePost(@PathVariable(name = "postId") Long postId) throws Exception {
        postsService.deletePost(postId);
        return postId + "번 포스트 삭제 완료";
    }

    //좋아요
    @GetMapping("/posts/{postId}/like")
    public int plusLikeCount(@PathVariable(name = "postId") Long postId, @LoginUser SessionUser sessionUser) throws Exception {
        return postsService.plusLikeCount(postId, sessionUser);
    }

    //좋아요 취소
    @GetMapping("/posts/{postId}/disLike")
    public int minusLikeCount(@PathVariable(name = "postId") Long postId, @LoginUser SessionUser sessionUser) throws Exception {
        return postsService.minusLikeCount(postId, sessionUser);
    }


}
