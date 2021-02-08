package me.portfolio.blog.web.dto.comments;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portfolio.blog.domain.comments.Comments;
import me.portfolio.blog.domain.posts.Posts;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentsResponseDto {
    private Posts posts;
    private Long id;
    private String body;
    private String author;
    private Comments parents;
    private LocalDateTime modifiedDate;

    public CommentsResponseDto(Comments entity){
        this.posts=entity.getPosts();
        this.id = entity.getId();
        this.body = entity.getBody();
        this.author = entity.getAuthor();
        this.parents = entity.getParents();
        this.modifiedDate=entity.getModifiedDate();

    }
}
