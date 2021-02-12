package me.portfolio.blog.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

//리눅스 서버에서는 로컬호스트가 필요없음
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://ec2-13-125-108-168.ap-northeast-2.compute.amazonaws.com", "http://localhost:8000, http://localhost:3000"}, allowCredentials = "true")
@Controller
public class IndexController {
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index() {
        return "/api/v2/posts";
    }

    @GetMapping("/api/v2/posts/user/logout")
    public String logout(){
        httpSession.removeAttribute("user");
        return "/api/v2/posts";
    }
}
