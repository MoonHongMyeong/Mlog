package me.portfolio.blog.config;

import lombok.RequiredArgsConstructor;
import me.portfolio.blog.config.auth.LoginUserArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginUserArgumentResolver loginUserArgumentResolver;
    //Nginx 에서 POST 할 때 정적파일이 들어가면 에러를 뱉는다. 그걸 방지
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/api/*").allowedOrigins("http://ec2-13-125-108-168.ap-northeast-2.compute.amazonaws.com")
        .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(),HttpMethod.PUT.name(),HttpMethod.DELETE.name(),HttpMethod.OPTIONS.name());
    }
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
        argumentResolvers.add(loginUserArgumentResolver);
    }
}
