package me.portpolio.blog.web.dto.comments;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portpolio.blog.domain.comments.Comments;
import me.portpolio.blog.domain.posts.Posts;

@Getter
@NoArgsConstructor
public class RepliesSaveRequestDto {
    private Posts posts;
    private Comments parents;
    private String author;
    private String body;

    @Builder
    public RepliesSaveRequestDto(Posts posts, Comments parents, String author, String body){
        this.posts=posts;
        this.parents=parents;
        this.author=author;
        this.body=body;
    }

    public Comments toEntity(){
        return Comments.builder()
                .posts(posts)
                .author(author)
                .body(body)
                .parents(parents)
                .build();
    }

}
