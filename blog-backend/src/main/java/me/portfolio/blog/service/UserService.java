package me.portfolio.blog.service;

import lombok.RequiredArgsConstructor;
import me.portfolio.blog.domain.user.User;
import me.portfolio.blog.domain.user.UserRepository;
import me.portfolio.blog.web.dto.user.UserUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    //회원 정보 조회

    //회원 수정
    @Transactional
    public Long updateUser(Long userId, UserUpdateRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        user.update(requestDto.getName(), requestDto.getPicture());
        return userId;
    }
    //회원 가입
    @Transactional
    public void secedeUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다"));
        userRepository.delete(user);
    }
}
