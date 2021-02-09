package me.portfolio.blog.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@CrossOrigin(origins = "http://ec2-13-125-108-168.ap-northeast-2.compute.amazonaws.com, " +
        "http://localhost:8000, http://localhost:3000")
@Controller
public class IndexController implements ErrorController {
    @GetMapping({"/", "/error"})
    public String home(){
        return "index.html";
    }

    @PostMapping("/posts")
    public String addPost(){
        return "/api/posts";
    }

    @Override
    public String getErrorPath(){
        return "/error";
    }
}
