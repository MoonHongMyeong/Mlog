package me.portfolio.blog.web.dto.categories;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portfolio.blog.domain.categories.Categories;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CategoriesListResponseDto {
    private Long id;
    private String name;
    private LocalDateTime modifiedDate;

    public CategoriesListResponseDto(Categories entity){
        this.id=entity.getId();
        this.name= entity.getName();
        this.modifiedDate=entity.getModifiedDate();
    }
}
