package me.portpolio.blog.service;

import lombok.RequiredArgsConstructor;
import me.portpolio.blog.domain.comments.Comments;
import me.portpolio.blog.domain.comments.CommentsRepository;
import me.portpolio.blog.domain.posts.Posts;
import me.portpolio.blog.domain.posts.PostsRepository;
import me.portpolio.blog.web.dto.comments.CommentsResponseDto;
import me.portpolio.blog.web.dto.comments.CommentsSaveRequestDto;
import me.portpolio.blog.web.dto.comments.CommentsUpdateRequestDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    public Long addComment(Long postId, CommentsSaveRequestDto requestDto){
        Posts postItem = postsRepository.findById(postId).get();
        requestDto.setPosts(postItem);
        Comments comments = commentsRepository.save(requestDto.toEntity());
        return comments.getId();
    }
    
    //댓글 수정
    @Transactional
    public Long updateComment(Long commentId, CommentsUpdateRequestDto requestDto){
        Comments comments = commentsRepository.findById(commentId).orElseThrow(
                ()->new IllegalArgumentException("해당 댓글이 없습니다. id="+commentId));
        comments.update(requestDto.getBody());
        return commentId;
    }
    
    //댓글 삭제
    public void deleteComment(Long commentId){
        Comments comments = commentsRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("해당 댓글이 없습니다. id="+commentId));
        commentsRepository.delete(comments);
    }


}
