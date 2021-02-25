package me.portfolio.blog.web.dto.posts;

import lombok.Getter;
import me.portfolio.blog.domain.categories.Categories;
import me.portfolio.blog.domain.posts.Posts;
import me.portfolio.blog.domain.user.User;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private Long id;
    private String title;
    private User user;
    private int likeCount;
    private String imageUrl;
    private Categories categories;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.user = entity.getUser();
        this.likeCount = entity.getLikeCount();
        this.categories = entity.getCategories();
        this.imageUrl = entity.getImageUrl();
        this.modifiedDate = entity.getModifiedDate();
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
