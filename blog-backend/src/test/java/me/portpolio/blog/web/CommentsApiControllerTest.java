package me.portpolio.blog.web;

import me.portpolio.blog.domain.comments.Comments;
import me.portpolio.blog.domain.comments.CommentsRepository;
import me.portpolio.blog.domain.posts.Posts;
import me.portpolio.blog.domain.posts.PostsRepository;
import me.portpolio.blog.web.dto.comments.CommentsSaveRequestDto;
import me.portpolio.blog.web.dto.comments.CommentsUpdateRequestDto;
import me.portpolio.blog.web.dto.comments.RepliesSaveRequestDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String,Object> body = new LinkedMultiValueMap<>();
        body.add("author", author);
        body.add("content", content);
        body.add("title", title);
        body.add("image", getFileResource());

        HttpEntity<MultiValueMap<String,Object>> requestEntity = new HttpEntity<>(body,headers);
        String url = "http://localhost:"+port+"/api/posts";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,requestEntity,String.class);

        assertThat(responseEntity.getStatusCode().is2xxSuccessful());
    }

    public static Resource getFileResource() throws Exception{
        Path tempFile = Files.createTempFile("upload-test-file",".png");
        Files.write(tempFile,"some test content".getBytes(StandardCharsets.UTF_8));
        File file = tempFile.toFile();
        return new FileSystemResource(file);
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
        Posts post = postsRepository.findById(3L).get();
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
        Posts post = postsRepository.findById(2L).get();

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
        Posts post = postsRepository.findById(4L).get();

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
