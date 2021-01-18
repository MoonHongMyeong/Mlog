package me.portpolio.blog.service;

import lombok.RequiredArgsConstructor;
import me.portpolio.blog.domain.posts.Posts;
import me.portpolio.blog.domain.posts.PostsRepository;
import me.portpolio.blog.web.dto.PostsResponseDto;
import me.portpolio.blog.web.dto.PostsSaveRequestDto;
import me.portpolio.blog.web.dto.PostsUpdateRequestDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

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
        Posts posts = postsRepository.findById(postId).orElseThrow(()->new IllegalArgumentException("해당 포스트가 없습니다. id="+postId));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return postId;
    }
}
