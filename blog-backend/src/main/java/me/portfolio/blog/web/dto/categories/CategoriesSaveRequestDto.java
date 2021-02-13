package me.portfolio.blog.web.dto.categories;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portfolio.blog.domain.categories.Categories;
import me.portfolio.blog.domain.user.User;

@Getter
@NoArgsConstructor
public class CategoriesSaveRequestDto {
    private User user;
    private String name;

    @Builder
    public CategoriesSaveRequestDto(User user, String name){
        this.user=user;
        this.name=name;
    }

    public Categories toEntity(){
        return Categories.builder().user(user).name(name).build();
    }
}
