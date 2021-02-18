package me.portfolio.blog.web.dto.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portfolio.blog.domain.categories.Categories;
import me.portfolio.blog.domain.posts.Posts;
import me.portfolio.blog.domain.user.User;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private User user;
    private int likeCount;
    private Categories categories;
    private String temp;
    private String imageUrl;

    @Builder
    public PostsSaveRequestDto(String title, String content, User user,String imageUrl, Categories categories, int likeCount, String temp){
        this.title=title;
        this.content=content;
        this.user=user;
        this.categories=categories;
        this.imageUrl=imageUrl;
        this.likeCount=likeCount;
        this.temp= temp;
    }

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .user(user)
                .categories(categories)
                .imageUrl(imageUrl)
                .likeCount(likeCount)
                .temp(temp)
                .build();
    }


}
