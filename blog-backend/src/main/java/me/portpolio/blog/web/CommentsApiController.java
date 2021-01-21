package me.portpolio.blog.web;

import lombok.RequiredArgsConstructor;
import me.portpolio.blog.domain.comments.Comments;
import me.portpolio.blog.service.CommentsService;
import me.portpolio.blog.web.dto.comments.CommentsResponseDto;
import me.portpolio.blog.web.dto.comments.CommentsSaveRequestDto;
import me.portpolio.blog.web.dto.comments.CommentsUpdateRequestDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class CommentsApiController {

    private final CommentsService commentsService;

    //댓글 리스트 조회
    @GetMapping("/{postsId}/comments")
    public List<Comments> getCommentList(@PathVariable Long postsId) throws Exception{
        return commentsService.getCommentList(postsId);
    }

    //댓글 등록
    @PostMapping("/{postsId}/comments")
    public Long addComment(@PathVariable Long postsId, @RequestBody CommentsSaveRequestDto requestDto) throws Exception{
        return commentsService.addComment(postsId, requestDto);
    }

    //댓글 수정
    @PutMapping("/{postsId}/comments/{commentsId}")
    public Long updateComment(@PathVariable Long commentsId, @RequestBody CommentsUpdateRequestDto requestDto) throws Exception{
        return commentsService.updateComment(commentsId, requestDto);
    }

    //댓글 삭제
    @DeleteMapping("/{postsId}/comments/{commentsId}")
    public Long deleteComment(@PathVariable Long commentsId) throws Exception{
        commentsService.deleteComment(commentsId);
        return commentsId;
    }
}
