package me.portfolio.blog.web.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portfolio.blog.domain.user.User;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private String name;
    private String email;
    private String picture;
    private String about;

    public UserResponseDto(User entity){
        this.name=entity.getName();
        this.email=entity.getEmail();
        this.picture=entity.getPicture();
        this.about=entity.getAbout();
    }
}
