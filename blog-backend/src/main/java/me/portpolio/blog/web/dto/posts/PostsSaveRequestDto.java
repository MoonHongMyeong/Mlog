package me.portpolio.blog.web.dto.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.portpolio.blog.domain.posts.Posts;

@Getter
@Setter
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
