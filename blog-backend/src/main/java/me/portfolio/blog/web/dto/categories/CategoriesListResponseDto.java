package me.portfolio.blog.web.dto.categories;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portfolio.blog.domain.categories.Categories;
import me.portfolio.blog.domain.posts.Posts;

import java.util.List;

@Getter
@NoArgsConstructor
public class CategoriesListResponseDto {
    private String name;
    private List<Posts> postInCategory;

    public CategoriesListResponseDto(Categories entity){
        this.name= entity.getName();
        this.postInCategory=entity.getPostsList();
    }
}
