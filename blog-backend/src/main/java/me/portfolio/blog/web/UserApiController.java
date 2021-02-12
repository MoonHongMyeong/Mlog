package me.portfolio.blog.web;

import lombok.RequiredArgsConstructor;
import me.portfolio.blog.service.UserService;
import me.portfolio.blog.web.dto.user.UserUpdateRequestDto;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin(origins = {"http://ec2-13-125-108-168.ap-northeast-2.compute.amazonaws.com", "http://localhost:8000", "http://localhost:3000"}, allowCredentials = "true")
@RequestMapping("/api/v2")
@RestController
public class UserApiController {

    private final UserService userService;

    //회원 수정
    @PutMapping("/user/{userId}")
    public Long updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequestDto requestDto) throws Exception{
        return userService.updateUser(userId, requestDto);
    }
    //회원 탈퇴
    @DeleteMapping("/user/{userId}")
    public Long secedeUser(@PathVariable Long userId) throws Exception{
        userService.secedeUser(userId);
        return userId;
    }
}
