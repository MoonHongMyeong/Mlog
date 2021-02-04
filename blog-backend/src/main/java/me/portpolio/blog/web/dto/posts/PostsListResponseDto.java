package me.portpolio.blog.web.dto.posts;

import lombok.Getter;
import me.portpolio.blog.domain.posts.Posts;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private Long id;
    private String title;
    private String author;
    private String imageUrl;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.imageUrl = entity.getImageUrl();
        this.modifiedDate = entity.getModifiedDate();
    }
}
