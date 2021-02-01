package me.portpolio.blog.service;

import lombok.RequiredArgsConstructor;
import me.portpolio.blog.domain.comments.Comments;
import me.portpolio.blog.domain.comments.CommentsRepository;
import me.portpolio.blog.domain.posts.Posts;
import me.portpolio.blog.domain.posts.PostsRepository;
import me.portpolio.blog.web.dto.comments.CommentsResponseDto;
import me.portpolio.blog.web.dto.comments.CommentsSaveRequestDto;
import me.portpolio.blog.web.dto.comments.CommentsUpdateRequestDto;
import me.portpolio.blog.web.dto.comments.RepliesSaveRequestDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CommentsService {
    private final PostsRepository postsRepository;
    private final CommentsRepository commentsRepository;

    //댓글 리스트 조회
    public List<Comments> getCommentList(Long postsId) {
        Posts post = postsRepository.findById(postsId).get();
        return post.getCommentsList();
    }

    //댓글 등록
    @Transactional
    public CommentsResponseDto addComment(Long postId, CommentsSaveRequestDto requestDto) {

        Posts postItem = postsRepository.findById(postId).get();
        requestDto.setPosts(postItem);

        Comments comments = commentsRepository.save(requestDto.toEntity());

        CommentsResponseDto responseDto = new CommentsResponseDto();
        responseDto.setPosts(comments.getPosts());
        responseDto.setId(comments.getId());
        responseDto.setBody(comments.getBody());
        responseDto.setAuthor(comments.getAuthor());
        responseDto.setParents(comments.getParents());
        responseDto.setModifiedDate(comments.getModifiedDate());
        return responseDto;
    }

    //대댓글 등록
    public CommentsResponseDto addReply(Long postId, Long parentsId, RepliesSaveRequestDto requestDto) {
        Posts postItem = postsRepository.findById(postId).get();
        Comments parentsItem = commentsRepository.findById(parentsId).get();

        requestDto.setPosts(postItem);
        requestDto.setComments(parentsItem);

        Comments reply = commentsRepository.save(requestDto.toEntity());

        CommentsResponseDto responseDto = new CommentsResponseDto();
        responseDto.setId(reply.getId());
        responseDto.setPosts(reply.getPosts());
        responseDto.setParents(reply.getParents());
        responseDto.setAuthor(reply.getAuthor());
        responseDto.setBody(reply.getBody());
        responseDto.setModifiedDate(reply.getModifiedDate());

        return responseDto;
    }

    //댓글 수정
    @Transactional
    public Long updateComment(Long commentId, CommentsUpdateRequestDto requestDto) {
        Comments comments = commentsRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 없습니다. id=" + commentId));
        comments.update(requestDto.getBody());
        return commentId;
    }

    //댓글 삭제
    public void deleteComment(Long commentId) {
        Comments comments = commentsRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 없습니다. id=" + commentId));
        commentsRepository.delete(comments);
    }

}
