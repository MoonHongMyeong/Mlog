package me.portfolio.blog.web.dto.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portfolio.blog.domain.posts.Posts;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;
    private String imageUrl;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author,String imageUrl){
        this.title=title;
        this.content=content;
        this.author=author;
        this.imageUrl=imageUrl;
    }

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .imageUrl(imageUrl)
                .build();
    }


}
