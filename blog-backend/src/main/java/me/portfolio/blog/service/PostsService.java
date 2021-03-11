package me.portfolio.blog.service;

import lombok.RequiredArgsConstructor;
import me.portfolio.blog.config.auth.dto.SessionUser;
import me.portfolio.blog.domain.categories.Categories;
import me.portfolio.blog.domain.categories.CategoriesRepositorySupport;
import me.portfolio.blog.domain.like.LikeVal;
import me.portfolio.blog.domain.like.LikeValRepository;
import me.portfolio.blog.domain.like.LikeValRepositorySupport;
import me.portfolio.blog.domain.user.User;
import me.portfolio.blog.domain.user.UserRepository;
import me.portfolio.blog.web.dto.posts.PostsListResponseDto;
import me.portfolio.blog.web.dto.posts.PostsResponseDto;
import me.portfolio.blog.web.dto.posts.PostsSaveRequestDto;
import me.portfolio.blog.web.dto.posts.PostsUpdateRequestDto;
import me.portfolio.blog.domain.posts.Posts;
import me.portfolio.blog.domain.posts.PostsRepository;
import me.portfolio.blog.domain.posts.PostsRepositorySupport;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Session;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final PostsRepositorySupport postsRepositorySupport;
    private final UserRepository userRepository;
    private final CategoriesRepositorySupport categoriesRepositorySupport;
    private final LikeValRepository likeValRepository;
    private final LikeValRepositorySupport likeValRepositorySupport;

    //포스트 리스트 조회
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> getPostList() {
        return postsRepositorySupport.findAllDesc()
                .stream()
                .map(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
    }

    //포스트 검색 리스트 조회
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> getListSearchTitle(String title) {
        return postsRepositorySupport.findByTitle(title)
                .stream()
                .map(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
    }
    //인기 포스트 조회
    public List<PostsListResponseDto> getPopPostList() {
        return postsRepositorySupport.findAllPop()
                .stream()
                .map(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
    }

    //포스트 등록
    @Transactional
    public Long addPost(SessionUser sessionUser,
                        String imgPath,
                        String title,
                        String content,
                        String categoryName) throws Exception{
        User user = checkSessionUser(sessionUser);
        Categories categories = checkCategories(sessionUser, categoryName);

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .user(user)
                .content(content)
                .imageUrl(imgPath)
                .categories(categories)
                .likeCount(0)
                .temp("N")
                .build();

        return postsRepository.save(requestDto.toEntity()).getId();
    }
    //세션 유저정보 불러오기
    private User checkSessionUser(SessionUser sessionUser) {
        return userRepository.findByEmail(sessionUser.getEmail()).get();
    }
    //카테고리 유무 체크
    private Categories checkCategories(SessionUser sessionUser, String categoryName) {
        if(categoryName != null) {
            return categoriesRepositorySupport.findByUserAndName(sessionUser.getId(), categoryName);
        }else{
            return null;
        }
    }

    //포스트 조회
    @Transactional(readOnly = true)
    public PostsResponseDto getPost(Long postId, SessionUser sessionUser){
        Posts entity = postsRepository.findById(postId)
                .orElseThrow(()->new IllegalArgumentException("해당 포스트가 없습니다. id="+postId));
        if(sessionUser != null) {
            PostsResponseDto responseDto = checkSessionUserIsLiked(sessionUser, entity);
            return responseDto;
        }else{
            return new PostsResponseDto(entity);
        }

    }
    //현재 세션에 접속한 유저가 해당 포스트의 좋아요 유무
    private PostsResponseDto checkSessionUserIsLiked(SessionUser sessionUser, Posts entity) {
        User user = userRepository.findByEmail(sessionUser.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인한 유저가 아닙니다."));
        LikeVal likeVal = likeValRepositorySupport.checkLikeWithUser(user,entity);

        PostsResponseDto responseDto = new PostsResponseDto(entity);
        if(likeVal != null){
            responseDto.isLiked(true);
        }else{
            responseDto.isLiked(false);
        }
        return responseDto;
    }


    //포스트 수정
    @Transactional
    public Long updatePost(Long postId, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(postId).orElseThrow(
                ()->new IllegalArgumentException("해당 포스트가 없습니다. id="+postId));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return postId;
    }
    //포스트 삭제
    @Transactional
    public void deletePost(Long postId){
        Posts posts = postsRepository.findById(postId).orElseThrow(
                ()->new IllegalArgumentException("해당 포스트가 없습니다. id="+postId));
        postsRepository.delete(posts);
    }
    //좋아요
    @Transactional
    public int plusLikeCount(Long postId, SessionUser sessionUser) {
        User user = userRepository.findByEmail(sessionUser.getEmail()).get();
        Posts post = postsRepository.findById(postId).get();

            likeValRepository.save(LikeVal.builder()
                    .user(user)
                    .posts(post)
                    .build());
            post.plusCount();


        return post.getLikeCount();
    }
    //좋아요 취소
    @Transactional
    public int minusLikeCount(Long postId, SessionUser sessionUser) {
        User user = userRepository.findByEmail(sessionUser.getEmail()).get();
        Posts post = postsRepository.findById(postId).get();

        LikeVal likeVal = likeValRepositorySupport.checkLikeWithUser(user, post);

            post.minusCount();
            likeValRepository.delete(likeVal);

        return post.getLikeCount();
    }
    //임시저장
    @Transactional
    public Long addTempPost(SessionUser sessionUser, PostsSaveRequestDto requestDto) {
        User user = userRepository.findByEmail(sessionUser.getEmail()).get();

        PostsSaveRequestDto saveRequestDto = PostsSaveRequestDto.builder()
                .temp("Y")
                .user(user)
                .likeCount(0)
                .content(requestDto.getContent())
                .title(requestDto.getTitle())
                .build();
        return postsRepository.save(saveRequestDto.toEntity()).getId();
    }
    //임시저장 목록 조회
    @Transactional(readOnly = true)
    public List<PostsResponseDto> getTempPost(SessionUser sessionUser) {
        return postsRepositorySupport.findTempPost(sessionUser)
                .stream()
                .map(temp -> new PostsResponseDto(temp))
                .collect(Collectors.toList());
    }
}
