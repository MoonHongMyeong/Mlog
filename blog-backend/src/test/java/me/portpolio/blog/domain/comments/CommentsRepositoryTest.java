package me.portpolio.blog.domain.comments;

import me.portpolio.blog.domain.posts.Posts;
import me.portpolio.blog.domain.posts.PostsRepository;
import me.portpolio.blog.web.dto.comments.CommentsResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class CommentsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;
    @Autowired
    CommentsRepository commentsRepository;

    @AfterEach
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void 댓글_저장_조회(){
        String body = "test comments body";
        String author = "test comments author";
        LocalDateTime now = LocalDateTime.now();

        Posts post = postsRepository.save(Posts.builder()
                .title("test title")
                .content("test content")
                .author("test author")
                .build());

        commentsRepository.save(Comments.builder()
                .posts(post)
                .body(body)
                .author(author)
                .build());

        List<Comments> commentsList = commentsRepository.findAll();
        Comments comments = commentsList.get(0);

        assertEquals(comments.getPosts().getId(), post.getId());
        assertEquals(comments.getBody(), body);
        assertEquals(comments.getAuthor(), author);
        assertThat(comments.getCreatedDate()).isAtLeast(now);
        assertThat(comments.getModifiedDate()).isAtLeast(now);
    }

    @Test
    public void 댓글_목록_조회(){
        Posts post = postsRepository.save(Posts.builder()
                .title("test title")
                .content("test content")
                .author("test author")
                .build());

        for(int i=1; i<=10; i++) {
            commentsRepository.save(Comments.builder()
                    .posts(post)
                    .body("test body"+i)
                    .author("test comment author"+i)
                    .build());
        }

        List<Comments> commentsList = commentsRepository.findAll();

        for(int i=0; i<10; i++){
            assertEquals(commentsList.get(i).getBody(), "test body"+(i+1));
            assertEquals(commentsList.get(i).getAuthor(), "test comment author"+(i+1));
            System.out.println(commentsList.get(i));
        }

    }

    @Test
    public void 대댓글_저장_조회(){
        String body = "test comments body";
        String author = "test comments author";
        LocalDateTime now = LocalDateTime.now();

        Posts post = postsRepository.save(Posts.builder()
                .title("test title")
                .content("test content")
                .author("test author")
                .build());

        Comments comments = commentsRepository.save(Comments.builder()
                .posts(post)
                .body(body)
                .author(author)
                .build());

        commentsRepository.save(Comments.builder()
                .posts(post)
                .parents(comments)
                .author("test replies author")
                .body("test replies body")
                .build());

        List<Comments> commentsList = commentsRepository.findAll();
        Comments parents = commentsList.get(0);
        assertEquals(parents.getPosts().getId(), post.getId());
        assertEquals(parents.getBody(), body);
        assertEquals(parents.getAuthor(), author);
        assertThat(parents.getCreatedDate()).isAtLeast(now);
        assertThat(parents.getModifiedDate()).isAtLeast(now);

        List<CommentsResponseDto> children = commentsRepository.findByParents(comments);
        assertEquals(children.get(0).getPosts().getId(), parents.getPosts().getId());
        assertEquals(children.get(0).getBody(), "test replies body");
        assertEquals(children.get(0).getAuthor(), "test replies author");
        assertEquals(children.get(0).getParents().getId(), parents.getId());
        assertThat(children.get(0).getModifiedDate()).isAtLeast(now);


    }

}
