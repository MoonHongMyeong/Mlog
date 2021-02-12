package me.portfolio.blog.web.dto.comments;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portfolio.blog.domain.comments.Comments;
import me.portfolio.blog.domain.posts.Posts;
import me.portfolio.blog.domain.user.User;


@Getter
@NoArgsConstructor
public class CommentsSaveRequestDto {
    private Posts posts;
    private String body;
    private User user;

    @Builder
    public CommentsSaveRequestDto(Posts posts, String body, User user) {
        this.posts = posts;
        this.body = body;
        this.user = user;
    }

    public Comments toEntity() {
        return Comments.builder()
                .body(body)
                .user(user)
                .posts(posts)
                .build();
    }
}
