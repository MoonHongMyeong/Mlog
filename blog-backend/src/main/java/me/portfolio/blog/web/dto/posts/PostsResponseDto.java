package me.portfolio.blog.web.dto.posts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portfolio.blog.domain.categories.Categories;
import me.portfolio.blog.domain.posts.Posts;
import me.portfolio.blog.domain.user.User;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private int likeCount;
    private User user;
    private Categories categories;
    private String temp;
    private String imageUrl;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private boolean like_val;

    public PostsResponseDto(Posts entity){
        this.id=entity.getId();
        this.title=entity.getTitle();
        this.content=entity.getContent();
        this.user=entity.getUser();
        this.temp=entity.getTemp();
        this.likeCount=entity.getLikeCount();
        this.categories=entity.getCategories();
        this.imageUrl=entity.getImageUrl();
        this.createdDate=entity.getCreatedDate();
        this.modifiedDate=entity.getModifiedDate();
    }

    public void isLiked(boolean like_val){
        this.like_val = like_val;
    }

    public void setLikeCount(int likeCount){
        this.likeCount=likeCount;
    }
}
