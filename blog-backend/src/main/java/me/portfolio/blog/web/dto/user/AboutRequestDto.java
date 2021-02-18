package me.portfolio.blog.web.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AboutRequestDto {
    private String about;
    @Builder
    public AboutRequestDto(String about){
        this.about = about;
    }
}
