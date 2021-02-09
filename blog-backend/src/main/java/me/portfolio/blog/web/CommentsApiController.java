package me.portfolio.blog.web;

import lombok.RequiredArgsConstructor;
import me.portfolio.blog.service.CommentsService;
import me.portfolio.blog.web.dto.comments.CommentsResponseDto;
import me.portfolio.blog.web.dto.comments.CommentsSaveRequestDto;
import me.portfolio.blog.web.dto.comments.CommentsUpdateRequestDto;
import me.portfolio.blog.web.dto.comments.RepliesSaveRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = {"http://ec2-13-125-108-168.ap-northeast-2.compute.amazonaws.com, http://localhost:8000, http://localhost:3000"}, allowCredentials = "true")
@RequestMapping("/api/posts")
public class CommentsApiController {

    private final CommentsService commentsService;

    //댓글 리스트 조회
    @GetMapping("/{postsId}/comments")
    public List<CommentsResponseDto> getCommentList(@PathVariable Long postsId) throws Exception{
        return commentsService.getCommentList(postsId);
    }

    //댓글 등록
    @PostMapping("/{postsId}/comments")
    public ResponseEntity<CommentsResponseDto> addComment(@PathVariable Long postsId, @RequestBody CommentsSaveRequestDto requestDto) throws Exception{
        CommentsResponseDto responseDto = commentsService.addComment(postsId ,requestDto);
        return new ResponseEntity<CommentsResponseDto>(responseDto, HttpStatus.OK);
    }
    //대댓글 등록
    @PostMapping("/{postId}/comments/{parentsId}")
    public ResponseEntity<CommentsResponseDto> addReply(@PathVariable Long postId, @PathVariable Long parentsId, @RequestBody RepliesSaveRequestDto requestDto){
        CommentsResponseDto responseDto = commentsService.addReply(postId, parentsId, requestDto);
        return new ResponseEntity<CommentsResponseDto>(responseDto, HttpStatus.OK);
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
