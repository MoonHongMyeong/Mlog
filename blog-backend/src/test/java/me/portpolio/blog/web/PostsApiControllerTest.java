package me.portpolio.blog.web;

import me.portpolio.blog.domain.posts.Posts;
import me.portpolio.blog.domain.posts.PostsRepository;
import me.portpolio.blog.web.dto.posts.PostsSaveRequestDto;
import me.portpolio.blog.web.dto.posts.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록() throws Exception{
        String title = "title";
        String content = "content";
        String author = "author";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        String url = "http://localhost:"+port+"/api/posts";
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url,requestDto,Long.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertEquals(all.get(0).getTitle(), title);
        assertEquals(all.get(0).getContent(), content);
        assertEquals(all.get(0).getAuthor(), author);
    }

    @Test
    public void Posts_수정() throws Exception{
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();
        String exceptedTitle = "update title";
        String exceptedContent = "update content";
        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(exceptedTitle)
                .content(exceptedContent)
                .build();
        String url = "http://localhost:"+port+"/api/posts/"+updateId;
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertEquals(all.get(0).getTitle(), exceptedTitle);
        assertEquals(all.get(0).getContent(), exceptedContent);
    }

    
    //@AfterEach 주석 처리해야 제대로 확인가능
    @Test
    public void Posts_삭제() throws Exception{
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long id = savedPosts.getId();
        String url = "http://localhost:"+port+"/api/posts/"+id;
        HttpEntity<Posts> requestEntity = new HttpEntity<>(savedPosts);
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Long.class);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful());
    }

    //파일 업로드 추가
    @Test
    public void Posts_등록_파일추가() throws Exception{
        MockMultipartHttpServletRequest multipartHttpServletRequest = new MockMultipartHttpServletRequest();
        multipartHttpServletRequest.setMethod("POST");
        multipartHttpServletRequest.setContentType("multipart/form-data");
        multipartHttpServletRequest.setRequestURI("/api/posts");

        multipartHttpServletRequest.addParameter("title","test title");
        multipartHttpServletRequest.addParameter("author","test author");
        multipartHttpServletRequest.addParameter("content","test content");
        FileInputStream fileInputStream = new FileInputStream(new File("/src/test/resource/test.png"));
        MockMultipartFile multipartFile = new MockMultipartFile("image","test.png","png",fileInputStream);
        multipartHttpServletRequest.addFile(multipartFile);


    }
}
