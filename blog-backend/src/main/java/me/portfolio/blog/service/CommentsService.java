package me.portfolio.blog.service;

import lombok.RequiredArgsConstructor;
import me.portfolio.blog.domain.comments.CommentsRepositorySupport;
import me.portfolio.blog.web.dto.comments.CommentsResponseDto;
import me.portfolio.blog.web.dto.comments.CommentsSaveRequestDto;
import me.portfolio.blog.web.dto.comments.CommentsUpdateRequestDto;
import me.portfolio.blog.web.dto.comments.RepliesSaveRequestDto;
import me.portfolio.blog.domain.comments.Comments;
import me.portfolio.blog.domain.comments.CommentsRepository;
import me.portfolio.blog.domain.posts.Posts;
import me.portfolio.blog.domain.posts.PostsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentsService {
    private final PostsRepository postsRepository;
    private final CommentsRepository commentsRepository;
    private final CommentsRepositorySupport commentsRepositorySupport;

    //댓글 리스트 조회
    @Transactional(readOnly = true)
    public List<CommentsResponseDto> getCommentList(Long postsId) {
        Posts postItem = postsRepository.findById(postsId).get();

        return commentsRepositorySupport.findByPosts(postItem)
                .stream()
                .map(comments -> new CommentsResponseDto(comments))
                .collect(Collectors.toList());

    }

    //댓글 등록
    @Transactional
    public CommentsResponseDto addComment(Long postId, CommentsSaveRequestDto requestDto) {

        Posts postItem = postsRepository.findById(postId).get();

        CommentsSaveRequestDto saveRequestDto
                = CommentsSaveRequestDto.builder()
                .posts(postItem)
                .author(requestDto.getAuthor())
                .body(requestDto.getBody())
                .build();

        Comments comments = commentsRepository.save(saveRequestDto.toEntity());

        CommentsResponseDto responseDto = new CommentsResponseDto(comments);

        return responseDto;
    }

    //대댓글 등록
    @Transactional
    public CommentsResponseDto addReply(Long postId, Long parentsId, RepliesSaveRequestDto requestDto) {

        Posts postItem = postsRepository.findById(postId).get();
        Comments parentsItem = commentsRepository.findById(parentsId).get();

        RepliesSaveRequestDto repliesDto = RepliesSaveRequestDto.builder()
                .posts(postItem)
                .parents(parentsItem)
                .author(requestDto.getAuthor())
                .body(requestDto.getBody())
                .build();

        Comments reply = commentsRepository.save(repliesDto.toEntity());

        CommentsResponseDto responseDto = new CommentsResponseDto(reply);

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
    @Transactional
    public void deleteComment(Long commentId) {
        Comments comments = commentsRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 없습니다. id=" + commentId));
        commentsRepository.delete(comments);
    }

}
