package me.portfolio.blog.web.dto.categories;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portfolio.blog.domain.categories.Categories;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CategoriesResponseDto {
    private String name;
    private LocalDateTime modifiedDate;

    public CategoriesResponseDto(Categories entity){
        this.name=entity.getName();
        this.modifiedDate=entity.getModifiedDate();
    }
}
