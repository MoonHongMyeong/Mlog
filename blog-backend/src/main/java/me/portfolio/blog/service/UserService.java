package me.portfolio.blog.service;

import lombok.RequiredArgsConstructor;
import me.portfolio.blog.domain.user.User;
import me.portfolio.blog.domain.user.UserRepository;
import me.portfolio.blog.domain.user.UserRepositorySupport;
import me.portfolio.blog.web.dto.categories.CategoriesListResponseDto;
import me.portfolio.blog.web.dto.comments.CommentsResponseDto;
import me.portfolio.blog.web.dto.posts.PostsListResponseDto;
import me.portfolio.blog.web.dto.user.UserResponseDto;
import me.portfolio.blog.web.dto.user.UserUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserRepositorySupport userRepositorySupport;

    //회원 정보 조회
    public UserResponseDto getUserInfo(Long userId) {
        User findUser = userRepository.findById(userId).get();
        return new UserResponseDto(findUser);
    }

    //해당 유저의 포스트 목록 조회
    public List<PostsListResponseDto> getUsersPosts(Long userId) {
        return userRepositorySupport.PostsByUser(userId)
                .stream()
                .map(post -> new PostsListResponseDto(post))
                .collect(Collectors.toList());
    }

    //해당 유저의 댓글 목록 조회
    public List<CommentsResponseDto> getUsersComments(Long userId) {
        return userRepositorySupport.CommentsByUser(userId)
                .stream()
                .map(comment -> new CommentsResponseDto(comment))
                .collect(Collectors.toList());
    }

    //회원 수정
    @Transactional
    public Long updateUser(Long userId, UserUpdateRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        user.update(requestDto.getName(), requestDto.getPicture());
        return userId;
    }
    //회원 탈퇴
    @Transactional
    public void secedeUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다"));
        userRepository.delete(user);
    }


}
