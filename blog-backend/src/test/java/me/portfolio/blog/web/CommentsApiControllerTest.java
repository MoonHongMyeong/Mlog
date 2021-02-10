package me.portfolio.blog.web;

import me.portfolio.blog.domain.comments.Comments;
import me.portfolio.blog.domain.comments.CommentsRepository;
import me.portfolio.blog.domain.posts.Posts;
import me.portfolio.blog.domain.posts.PostsRepository;
import me.portfolio.blog.web.dto.comments.CommentsSaveRequestDto;
import me.portfolio.blog.web.dto.comments.CommentsUpdateRequestDto;
import me.portfolio.blog.web.dto.comments.RepliesSaveRequestDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

import static com.google.common.truth.Truth.*;
import static org.junit.jupiter.api.Assertions.*;

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

//    @AfterEach
//    public void tearDown() throws Exception{
//        postsRepository.deleteAll();
//    }

    @Test
    public void Comments_등록() throws Exception{
        Posts post = postsRepository.save(Posts.builder()
                .title("test post title")
                .author("test post author")
                .content("test post content")
                .build());

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
        Posts post = postsRepository.save(Posts.builder()
                .title("test post title")
                .author("test post author")
                .content("test post content")
                .build());

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
        Posts post = postsRepository.save(Posts.builder()
                .title("test post title")
                .author("test post author")
                .content("test post content")
                .build());

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

    @Test
    public void Replies_등록(){
        Posts post = postsRepository.save(Posts.builder()
                .title("test post title")
                .author("test post author")
                .content("test post content")
                .build());

        Comments parents = commentsRepository.save(Comments.builder()
                .author("parents author")
                .body("parents body")
                .posts(post)
                .build());

        RepliesSaveRequestDto children = RepliesSaveRequestDto.builder()
                .author("children author")
                .body("children body")
                .posts(post)
                .parents(parents)
                .build();

        Long parent_id = parents.getId();
        System.out.println("=================================================");
        System.out.println(parent_id);
        String url ="http://localhost:"+port+"api/posts/"+post.getId()+"/comments/"+parent_id;

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, children, String.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

    }

}
