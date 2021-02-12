package me.portfolio.blog.web.dto.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portfolio.blog.domain.posts.Posts;
import me.portfolio.blog.domain.user.User;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private User user;
    private String imageUrl;

    @Builder
    public PostsSaveRequestDto(String title, String content, User user,String imageUrl){
        this.title=title;
        this.content=content;
        this.user=user;
        this.imageUrl=imageUrl;
    }

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .user(user)
                .imageUrl(imageUrl)
                .build();
    }


}
