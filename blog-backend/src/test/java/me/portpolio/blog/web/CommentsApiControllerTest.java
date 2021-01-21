package me.portpolio.blog.web;

import me.portpolio.blog.domain.comments.Comments;
import me.portpolio.blog.domain.comments.CommentsRepository;
import me.portpolio.blog.domain.posts.Posts;
import me.portpolio.blog.domain.posts.PostsRepository;
import me.portpolio.blog.web.dto.comments.CommentsSaveRequestDto;
import me.portpolio.blog.web.dto.comments.CommentsUpdateRequestDto;
import me.portpolio.blog.web.dto.posts.PostsSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.google.common.truth.Truth.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    @AfterEach
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @BeforeEach
    public void Posts_등록() throws Exception {
        String title = "title";
        String content = "content";
        String author = "author";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        String url = "http://localhost:" + port + "/api/posts";
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void Comments_등록() throws Exception{
        Posts post = postsRepository.findById(1L).get();

        CommentsSaveRequestDto requestDto = CommentsSaveRequestDto.builder()
                .posts(post)
                .body("test comment body")
                .author("test comment author")
                .build();

        String url = "http://localhost:"+port+"/api/posts/"+post.getId()+"/comments";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,requestDto,String.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void Comments_수정() throws Exception{
        Posts post = postsRepository.findById(1L).get();
        Comments savedComments = commentsRepository.save(Comments.builder()
                .body("body")
                .author("author")
                .posts(post)
                .build());

        Long updateId = savedComments.getId();
        String exceptedBody = "update body";

        CommentsUpdateRequestDto requestDto = CommentsUpdateRequestDto.builder()
                .body(exceptedBody)
                .build();

        String url = "http://localhost:"+port+"/api/posts/"+post.getId()+"/comments/"+updateId;
        HttpEntity<CommentsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity,Long.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Comments> all = commentsRepository.findAll();
        assertEquals(all.get(0).getBody(), exceptedBody);
    }

    @Test
    public void Comments_삭제(){
        Posts post = postsRepository.findById(1L).get();

        Comments savedComments = commentsRepository.save(Comments.builder()
                .author("author")
                .body("body")
                .posts(post)
                .build());

        Long id = savedComments.getId();
        String url = "http://localhost:"+port+"/api/posts/"+post.getId()+"/comments/"+id;
        HttpEntity<Comments> requestEntity = new HttpEntity<>(savedComments);
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Long.class);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful());
    }

}
