package me.portfolio.blog.service;

import lombok.RequiredArgsConstructor;
import me.portfolio.blog.config.auth.dto.SessionUser;
import me.portfolio.blog.domain.user.User;
import me.portfolio.blog.domain.user.UserRepository;
import me.portfolio.blog.web.dto.posts.PostsListResponseDto;
import me.portfolio.blog.web.dto.posts.PostsResponseDto;
import me.portfolio.blog.web.dto.posts.PostsSaveRequestDto;
import me.portfolio.blog.web.dto.posts.PostsUpdateRequestDto;
import me.portfolio.blog.domain.posts.Posts;
import me.portfolio.blog.domain.posts.PostsRepository;
import me.portfolio.blog.domain.posts.PostsRepositorySupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final PostsRepositorySupport postsRepositorySupport;
    private final UserRepository userRepository;

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

    //포스트 등록
    @Transactional
    public Long addPost(SessionUser sessionUser, MultipartFile image, String title, String content) throws IOException {
        //세션 유저 정보 불러오기
        User user = userRepository.findByEmail(sessionUser.getEmail()).get();

        //파일 저장
        if (image == null) {
            PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                    .title(title)
                    .user(user)
                    .content(content)
                    .imageUrl("/images/default.png")
                    .build();

            return postsRepository.save(requestDto.toEntity()).getId();

        } else {
            //로컬 테스트용
            String baseDir = "D:\\GitHub\\Blog-portfolio\\blog-springboot-react\\blog-frontend\\public\\images";
            String filePath = baseDir + "\\" + image.getOriginalFilename();
            //실제 리눅스 서버 배포용
//            String baseDir = "/home/ec2-user/portfolio-blog/blog-frontend/build/images";
//            String filePath = baseDir + "/" + image.getOriginalFilename();
            image.transferTo(new File(filePath));
            String fileName = image.getOriginalFilename();

            PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                    .title(title)
                    .user(user)
                    .content(content)
                    .imageUrl("/images/" + fileName)
                    .build();
            return postsRepository.save(requestDto.toEntity()).getId();
        }
    }

    //포스트 조회
    @Transactional(readOnly = true)
    public PostsResponseDto getPost(Long postId){
        Posts entity = postsRepository.findById(postId).orElseThrow(
                ()->new IllegalArgumentException("해당 포스트가 없습니다. id="+postId));

        return new PostsResponseDto(entity);
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


}
