package me.portfolio.blog.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.google.common.truth.Truth.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;
//    @AfterEach
//    public void tearDown() throws Exception{
//        postsRepository.deleteAll();
//    }

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Comments_등록() throws Exception {
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

        String url = "http://localhost:" + port + "/api/v2/posts/" + post.getId() + "/comments";

        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        postsRepository.deleteById(post.getId());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Comments_수정() throws Exception {
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

        String url = "http://localhost:" + port + "/api/v2/posts/" + post.getId() + "/comments/" + updateId;

        mvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        postsRepository.deleteById(post.getId());
    }

    @Test
    public void Comments_삭제() throws Exception {
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
        String url = "http://localhost:" + port + "/api/v2/posts/" + post.getId() + "/comments/" + id;

        mvc.perform(delete(url)).andExpect(status().isOk());

        postsRepository.deleteById(post.getId());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Replies_등록() throws Exception {
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
        String url = "http://localhost:" + port + "api/v2/posts/" + post.getId() + "/comments/" + parent_id;

        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(children)))
                .andExpect(status().is(200));

        postsRepository.deleteById(post.getId());

    }

}
