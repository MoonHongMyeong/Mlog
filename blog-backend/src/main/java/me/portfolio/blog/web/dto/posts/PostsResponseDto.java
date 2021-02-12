package me.portfolio.blog.web.dto.posts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portfolio.blog.domain.posts.Posts;
import me.portfolio.blog.domain.user.User;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private User user;
    private String imageUrl;
    private LocalDateTime modifiedDate;

    public PostsResponseDto(Posts entity){
        this.id=entity.getId();
        this.title=entity.getTitle();
        this.content=entity.getContent();
        this.user=entity.getUser();
        this.imageUrl=entity.getImageUrl();
        this.modifiedDate=entity.getModifiedDate();
    }
}
