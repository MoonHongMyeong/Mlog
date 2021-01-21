package me.portpolio.blog.web.dto.comments;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentsUpdateRequestDto {
    private String body;

    @Builder
    public CommentsUpdateRequestDto(String body){
        this.body = body;
    }
}
