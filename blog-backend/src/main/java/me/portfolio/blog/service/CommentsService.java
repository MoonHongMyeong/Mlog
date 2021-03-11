package me.portfolio.blog.service;

import lombok.RequiredArgsConstructor;
import me.portfolio.blog.config.auth.dto.SessionUser;
import me.portfolio.blog.domain.comments.CommentsRepositorySupport;
import me.portfolio.blog.domain.user.User;
import me.portfolio.blog.domain.user.UserRepository;
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
    private final UserRepository userRepository;

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
    public CommentsResponseDto addComment(SessionUser sessionUser, Long postId, CommentsSaveRequestDto requestDto) {

        Posts post = getPost(postId);
        User user = getSessionUser(sessionUser);
        CommentsSaveRequestDto saveRequestDto
                = CommentsSaveRequestDto.builder()
                .posts(post)
                .user(user)
                .body(requestDto.getBody())
                .build();

        Comments comments = commentsRepository.save(saveRequestDto.toEntity());

        CommentsResponseDto responseDto = new CommentsResponseDto(comments);

        return responseDto;
    }

    private Posts getPost(Long postId){
        return postsRepository.findById(postId).get();
    }
    private User getSessionUser(SessionUser sessionUser){
        return userRepository.findByEmail(sessionUser.getEmail()).get();
    }

    //대댓글 등록
    @Transactional
    public CommentsResponseDto addReply(SessionUser sessionUser, Long postId, Long parentsId, RepliesSaveRequestDto requestDto) {
        User user =getSessionUser(sessionUser);
        Posts post = getPost(postId);
        Comments parentsItem = getParents(parentsId);

        RepliesSaveRequestDto repliesDto = RepliesSaveRequestDto.builder()
                .posts(post)
                .parents(parentsItem)
                .user(user)
                .body(requestDto.getBody())
                .build();

        Comments reply = commentsRepository.save(repliesDto.toEntity());

        CommentsResponseDto responseDto = new CommentsResponseDto(reply);

        return responseDto;
    }
    private Comments getParents(Long parentsId){
        return commentsRepository.findById(parentsId).get();
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
