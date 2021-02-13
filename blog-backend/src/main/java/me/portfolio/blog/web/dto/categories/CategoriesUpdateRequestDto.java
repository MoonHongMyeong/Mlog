package me.portfolio.blog.web.dto.categories;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoriesUpdateRequestDto {
    private String name;

    @Builder
    public CategoriesUpdateRequestDto(String name){
        this.name=name;
    }
}
