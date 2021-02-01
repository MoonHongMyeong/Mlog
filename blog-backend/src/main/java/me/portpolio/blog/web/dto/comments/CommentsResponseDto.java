package me.portpolio.blog.web.dto.comments;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.portpolio.blog.domain.comments.Comments;
import me.portpolio.blog.domain.posts.Posts;

import java.time.LocalDateTime;

@Getter
@Setter
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
