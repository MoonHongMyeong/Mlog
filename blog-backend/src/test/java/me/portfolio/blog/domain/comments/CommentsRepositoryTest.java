package me.portfolio.blog.domain.comments;

import me.portfolio.blog.domain.posts.Posts;
import me.portfolio.blog.domain.posts.PostsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommentsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    CommentsRepository commentsRepository;

    @Autowired
    CommentsQueryRepository commentsQueryRepository;

//    @AfterEach
//    public void cleanup(){
//        postsRepository.deleteAll();
//    }

    //passed
    @Test
    public void 댓글_저장_조회(){
        String body = "test comments body";
        String author = "test comments author";
        LocalDateTime now = LocalDateTime.of(2021,2,7,0,0,0);

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
        Comments comments = commentsList.get((commentsList.size() - 1));

        assertEquals(comments.getPosts().getId(), post.getId());
        assertEquals(comments.getBody(), body);
        assertEquals(comments.getAuthor(), author);
        assertThat(comments.getCreatedDate()).isAtLeast(now);
        assertThat(comments.getModifiedDate()).isAtLeast(now);

        postsRepository.deleteById(post.getId());
    }

    //passed
    @Test
    public void 댓글_목록_조회(){
        Posts post = postsRepository.save(Posts.builder()
                .title("comment test title")
                .content("comment test content")
                .author("comment test author")
                .build());

        for(int i=1; i<=10; i++) {
            commentsRepository.save(Comments.builder()
                    .posts(post)
                    .body("test body")
                    .author("test comment author")
                    .build());
        }

        List<Comments> commentsList = commentsRepository.findAll();

        for(int i=commentsList.size()-1 ; i>commentsList.size()-11; i--){
            assertEquals(commentsList.get(i).getBody(), "test body");
            assertEquals(commentsList.get(i).getAuthor(), "test comment author");

        }

        postsRepository.deleteById(post.getId());
    }

    //passed
    @Test
    public void 대댓글_저장_조회(){
        String body = "test comments body";
        String author = "test comments author";
        LocalDateTime now = LocalDateTime.of(2020, 02, 07, 0,0,0);

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
        Comments parents = commentsList.get((commentsList.size()-2));
        assertEquals(parents.getPosts().getId(), post.getId());
        assertEquals(parents.getBody(), body);
        assertEquals(parents.getAuthor(), author);
        assertThat(parents.getCreatedDate()).isAtLeast(now);
        assertThat(parents.getModifiedDate()).isAtLeast(now);

        List<Comments> children = commentsQueryRepository.findByParents(comments);
        assertEquals(children.get(0).getPosts().getId(), parents.getPosts().getId());
        assertEquals(children.get(0).getBody(), "test replies body");
        assertEquals(children.get(0).getAuthor(), "test replies author");
        assertEquals(children.get(0).getParents().getId(), parents.getId());
        assertThat(children.get(0).getModifiedDate()).isAtLeast(now);

        postsRepository.deleteById(post.getId());
    }

}
