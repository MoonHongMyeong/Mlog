package me.portfolio.blog.web;

import lombok.RequiredArgsConstructor;
import me.portfolio.blog.config.auth.LoginUser;
import me.portfolio.blog.config.auth.dto.SessionUser;
import me.portfolio.blog.service.UserService;
import me.portfolio.blog.web.dto.comments.CommentsResponseDto;
import me.portfolio.blog.web.dto.posts.PostsListResponseDto;
import me.portfolio.blog.web.dto.user.AboutRequestDto;
import me.portfolio.blog.web.dto.user.UserResponseDto;
import me.portfolio.blog.web.dto.user.UserUpdateRequestDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin(origins = {"https://accounts.google.com","http://ec2-13-125-108-168.ap-northeast-2.compute.amazonaws.com", "http://localhost:8000", "http://localhost:3000"}, allowCredentials = "true")
@RequestMapping("/api/v2")
@RestController
public class UserApiController {

    private final UserService userService;
    //현재 세션에 로그인 한 유저 정보
    @GetMapping("/user")
    public UserResponseDto getUserInSession(@LoginUser SessionUser sessionUser) throws Exception{
        if(sessionUser != null){
            UserResponseDto responseDto =  userService.getLoginUser(sessionUser);
            return responseDto;
        }
        return null;
    }
    //회원 정보 조회
    @GetMapping("/user/{userId}")
    public UserResponseDto getUserInfo(@PathVariable(name = "userId") Long userId) throws Exception{
        return userService.getUserInfo(userId);
    }
    //해당 유저의 포스트 목록 조회
    @GetMapping("/user/{userId}/posts")
    public List<PostsListResponseDto> getUsersPosts(@PathVariable(name = "userId") Long userId) throws Exception{
        return userService.getUsersPosts(userId);
    }
    //해당 유저의 코멘트 목록 조회
    @GetMapping("/user/{userId}/comments")
    public List<CommentsResponseDto> getUsersComments(@PathVariable(name = "userId") Long userId) throws Exception{
        return userService.getUsersComments(userId);
    }
    //소개 글 수정
    @PutMapping("/user/{userId}/about")
    public UserResponseDto updateUserAbout(@RequestBody AboutRequestDto requestDto, @PathVariable(name = "userId") Long userId)throws Exception{
        return userService.updateUserAbout(requestDto, userId);
    }
    //회원 수정
    @PutMapping("/user/{userId}")
    public Long updateUser(@PathVariable(name = "userId") Long userId, @RequestBody UserUpdateRequestDto requestDto) throws Exception{
        return userService.updateUser(userId, requestDto);
    }
    //회원 탈퇴
    @DeleteMapping("/user/{userId}")
    public Long secedeUser(@PathVariable(name = "userId") Long userId) throws Exception{
        userService.secedeUser(userId);
        return userId;
    }
    

}
