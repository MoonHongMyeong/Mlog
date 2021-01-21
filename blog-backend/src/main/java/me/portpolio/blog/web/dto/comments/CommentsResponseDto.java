package me.portpolio.blog.web.dto.comments;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portpolio.blog.domain.comments.Comments;

@Getter
@NoArgsConstructor
public class CommentsResponseDto {
    private Long id;
    private String body;
    private String author;
    private Long parentId;
    private Long coClass;
    private Long coOrder;

    public CommentsResponseDto(Comments entity){
        this.id = entity.getId();
        this.body = entity.getBody();
        this.author = entity.getAuthor();
        this.parentId = entity.getParentId();
        this.coClass = entity.getCoClass();
        this.coOrder = entity.getCoOrder();
    }
}
