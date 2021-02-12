package me.portfolio.blog.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.portfolio.blog.domain.posts.Posts;
import me.portfolio.blog.domain.posts.PostsRepository;
import me.portfolio.blog.domain.user.Role;
import me.portfolio.blog.domain.user.User;
import me.portfolio.blog.domain.user.UserRepository;
import me.portfolio.blog.web.dto.posts.PostsSaveRequestDto;
import me.portfolio.blog.web.dto.posts.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("local")
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mvc;

    @AfterEach
    public void cleanup(){
        List<User> userList = userRepository.findAll();
        User testUser = userList.get((userList.size()-1));
        userRepository.deleteById(testUser.getId());
    }

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        User testUser = userRepository.save(User.builder()
                .email("test@test.com")
                .name("test user")
                .picture("/images/default")
                .role(Role.USER).build());
    }


    //passed
    @Test
    @WithMockUser(roles = "USER")
    public void Posts_등록_파일있음() throws Exception {
        String content = "test posts content";
        String title = "test posts title";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .user(userRepository.findByEmail("test@test.com").get())
                .build();

        String url = "http://localhost:" + port + "/api/v2/posts";

        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("user", userRepository.findByEmail("test@test.com").get());

        mvc.perform(multipart(url).file(new MockMultipartFile("image","test","image/png","/test.png".getBytes()))
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .session(mockHttpSession)
                .param("content", content)
                .param("title", title))
                .andDo(print())
                .andExpect(status().isOk());


        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    public static Resource getFileResource() throws Exception {
        Path tempFile = Files.createTempFile("upload-test-file", ".png");
        Files.write(tempFile, "some test content".getBytes(StandardCharsets.UTF_8));
        File file = tempFile.toFile();
        return new FileSystemResource(file);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Posts_등록_파일없음() throws Exception {
        String content = "test posts content";
        String title = "test posts title";
        String url = "http://localhost:" + port + "/api/v2/posts";

        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("user", userRepository.findByEmail("test@test.com").get());

        mvc.perform(multipart(url)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .session(mockHttpSession)
                .param("content", content)
                .param("title", title))
                .andDo(print())
                .andExpect(status().isOk());

        List<Posts> postsList = postsRepository.findAll();
        Posts posts = postsList.get((postsList.size() - 1));
        postsRepository.deleteById(posts.getId());
    }

    //passed
    @Test
    @WithMockUser(roles = "USER")
    public void Posts_수정() throws Exception {

        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .user(userRepository.findByEmail("test@test.com").get())
                .build());

        Long updateId = savedPosts.getId();
        String exceptedTitle = "update title";
        String exceptedContent = "update content";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(exceptedTitle)
                .content(exceptedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v2/posts/" + updateId;

        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<Posts> all = postsRepository.findAll();
        Posts posts = all.get((all.size() - 1));

        assertThat(posts.getTitle().equals(exceptedTitle));
        assertThat(posts.getContent().equals(exceptedContent));


        postsRepository.deleteById(posts.getId());
    }


    //@AfterEach 주석 처리해야 테스트 패스함
    @Test
    @WithMockUser(roles = "USER")
    public void Posts_삭제() throws Exception {
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .user(userRepository.findByEmail("test@test.com").get())
                .build());

        Long id = savedPosts.getId();
        String url = "http://localhost:" + port + "/api/v2/posts/" + id;

        mvc.perform(delete(url)).andExpect(status().isOk());
    }

}
