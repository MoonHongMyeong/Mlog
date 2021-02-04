package me.portpolio.blog.web.dto.comments;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portpolio.blog.domain.comments.Comments;
import me.portpolio.blog.domain.posts.Posts;


@Getter
@NoArgsConstructor
public class CommentsSaveRequestDto {
    private Posts posts;
    private String body;
    private String author;

    @Builder
    public CommentsSaveRequestDto(Posts posts, String body, String author) {
        this.posts = posts;
        this.body = body;
        this.author = author;
    }

    public Comments toEntity() {
        return Comments.builder()
                .body(body)
                .author(author)
                .posts(posts)
                .build();
    }
}
