package me.portpolio.blog.web.dto.posts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portpolio.blog.domain.posts.Posts;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime modifiedDate;

    public PostsResponseDto(Posts entity){
        this.id=entity.getId();
        this.title=entity.getTitle();
        this.content=entity.getContent();
        this.author=entity.getAuthor();
        this.modifiedDate=entity.getModifiedDate();
    }
}
