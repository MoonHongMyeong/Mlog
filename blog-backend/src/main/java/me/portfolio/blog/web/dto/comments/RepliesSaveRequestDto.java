package me.portfolio.blog.web.dto.comments;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portfolio.blog.domain.comments.Comments;
import me.portfolio.blog.domain.posts.Posts;
import me.portfolio.blog.domain.user.User;

@Getter
@NoArgsConstructor
public class RepliesSaveRequestDto {
    private Posts posts;
    private Comments parents;
    private User user;
    private String body;

    @Builder
    public RepliesSaveRequestDto(Posts posts, Comments parents, User user, String body){
        this.posts=posts;
        this.parents=parents;
        this.user=user;
        this.body=body;
    }

    public Comments toEntity(){
        return Comments.builder()
                .posts(posts)
                .user(user)
                .body(body)
                .parents(parents)
                .build();
    }

}
