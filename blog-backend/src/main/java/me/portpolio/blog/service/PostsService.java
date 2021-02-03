package me.portpolio.blog.service;

import lombok.RequiredArgsConstructor;
import me.portpolio.blog.domain.posts.Posts;
import me.portpolio.blog.domain.posts.PostsRepository;
import me.portpolio.blog.domain.posts.PostsRepositorySupport;
import me.portpolio.blog.web.dto.posts.PostsResponseDto;
import me.portpolio.blog.web.dto.posts.PostsSaveRequestDto;
import me.portpolio.blog.web.dto.posts.PostsUpdateRequestDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final PostsRepositorySupport postsRepositorySupport;

    //포스트 리스트 조회 제목 검색기능 추가
    public List<Posts> getPostList(String search) {
        if(search == null){
            return postsRepository.findAll();
        }
        return postsRepositorySupport.findByTitle(search);
    }

    //포스트 등록
    @Transactional
    public Long addPost(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    //포스트 조회
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
    public void deletePost(Long postId){
        Posts posts = postsRepository.findById(postId).orElseThrow(
                ()->new IllegalArgumentException("해당 포스트가 없습니다. id="+postId));
        postsRepository.delete(posts);
    }


}
