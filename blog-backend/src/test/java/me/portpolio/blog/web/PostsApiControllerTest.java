package me.portpolio.blog.web;

import me.portpolio.blog.domain.posts.Posts;
import me.portpolio.blog.domain.posts.PostsRepository;
import me.portpolio.blog.web.dto.posts.PostsResponseDto;
import me.portpolio.blog.web.dto.posts.PostsSaveRequestDto;
import me.portpolio.blog.web.dto.posts.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
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


    //passed
    @Test
    public void Posts_등록() throws Exception{
        String author = "test posts author";
        String content = "test posts content";
        String title = "test posts title";

//        String filePath = "D:\\GitHub\\Blog-portfolio\\blog-springboot-react\\blog-backend\\src\\test\\resources\\test.png";
//        MultipartFile file = new MockMultipartFile("test.png", new FileInputStream(new File(filePath)));

//        1.그냥 file을 add해서 사용하면 ByteArrayInputStream 에서 에러가 발생함 바이트 문제인것 같아서 해결하기 위해 새로 resource 로 가져옴
//        2.여전히 400 에러가 발생함
//        ByteArrayResource fileResource = new ByteArrayResource(file.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String,Object> body = new LinkedMultiValueMap<>();
        body.add("author", author);
        body.add("content", content);
        body.add("title", title);
//        3. 그래서 그냥 리소스 통쨰로 메서드로 불러옴
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

    //passed
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
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful());
    }

}
