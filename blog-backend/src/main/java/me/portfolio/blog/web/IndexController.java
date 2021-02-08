package me.portfolio.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@CrossOrigin(origins = "http://ec2-13-125-108-168.ap-northeast-2.compute.amazonaws.com:8000, http://ec2-13-125-108-168.ap-northeast-2.compute.amazonaws.com:3000")
@Controller
public class IndexController {
    @RequestMapping("/")
    public String home(){
        return "/api/posts";
    }

    @PostMapping("/posts")
    public String addPost(){
        return "/api/posts";
    }
}
