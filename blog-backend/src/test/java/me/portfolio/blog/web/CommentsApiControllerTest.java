package me.portfolio.blog.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.portfolio.blog.domain.comments.Comments;
import me.portfolio.blog.domain.comments.CommentsRepository;
import me.portfolio.blog.domain.posts.Posts;
import me.portfolio.blog.domain.posts.PostsRepository;
import me.portfolio.blog.domain.user.Role;
import me.portfolio.blog.domain.user.User;
import me.portfolio.blog.domain.user.UserRepository;
import me.portfolio.blog.web.dto.comments.CommentsSaveRequestDto;
import me.portfolio.blog.web.dto.comments.CommentsUpdateRequestDto;
import me.portfolio.blog.web.dto.comments.RepliesSaveRequestDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

    @Autowired
    private UserRepository userRepository;

    private MockMvc mvc;

    @AfterEach
    public void cleanup(){
        User user = userRepository.findByEmail("test@test.com").get();
        User guestUser = userRepository.findByEmail("guest@test.com").get();
        userRepository.delete(user);
        userRepository.delete(guestUser);
    }

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        User testUser = userRepository.save(User.builder()
                .email("test@test.com")
                .name("test user")
                .picture("/images/default")
                .role(Role.USER).build());

        User guestUser = userRepository.save(User.builder()
                .email("guest@test.com")
                .name("guest user")
                .picture("/images/default")
                .role(Role.USER).build());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 사용자_Comments_등록() throws Exception {
        Posts post = postsRepository.save(Posts.builder()
                .title("test post title")
                .user(userRepository.findByEmail("test@test.com").get())
                .content("test post content")
                .build());

        CommentsSaveRequestDto requestDto = CommentsSaveRequestDto.builder()
                .posts(post)
                .body("test comment body")
                .user(userRepository.findByEmail("test@test.com").get())
                .build();

        String url = "http://localhost:" + port + "/api/v2/posts/" + post.getId() + "/comments";

        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        postsRepository.deleteById(post.getId());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 게스트_Comments_등록() throws Exception {
        Posts post = postsRepository.save(Posts.builder()
                .title("test post title")
                .user(userRepository.findByEmail("test@test.com").get())
                .content("test post content")
                .build());

        CommentsSaveRequestDto requestDto = CommentsSaveRequestDto.builder()
                .posts(post)
                .body("test comment body")
                .user(userRepository.findByEmail("guest@test.com").get())
                .build();

        String url = "http://localhost:" + port + "/api/v2/posts/" + post.getId() + "/comments";

        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        postsRepository.deleteById(post.getId());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 사용자_Comments_수정() throws Exception {
        Posts post = postsRepository.save(Posts.builder()
                .title("test post title")
                .user(userRepository.findByEmail("test@test.com").get())
                .content("test post content")
                .build());

        Comments savedComments = commentsRepository.save(Comments.builder()
                .body("body")
                .user(userRepository.findByEmail("test@test.com").get())
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
    @WithMockUser(roles = "GUEST")
    public void 게스트_Comments_수정() throws Exception {
        Posts post = postsRepository.save(Posts.builder()
                .title("test post title")
                .user(userRepository.findByEmail("test@test.com").get())
                .content("test post content")
                .build());

        Comments savedComments = commentsRepository.save(Comments.builder()
                .body("body")
                .user(userRepository.findByEmail("guest@test.com").get())
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
    @WithMockUser(roles = "USER")
    public void 사용자_Comments_삭제() throws Exception {
        Posts post = postsRepository.save(Posts.builder()
                .title("test post title")
                .user(userRepository.findByEmail("test@test.com").get())
                .content("test post content")
                .build());

        Comments savedComments = commentsRepository.save(Comments.builder()
                .user(userRepository.findByEmail("test@test.com").get())
                .body("body")
                .posts(post)
                .build());

        Long id = savedComments.getId();
        String url = "http://localhost:" + port + "/api/v2/posts/" + post.getId() + "/comments/" + id;

        mvc.perform(delete(url)).andExpect(status().isOk());

        postsRepository.deleteById(post.getId());
    }

    @Test
    @WithMockUser(roles = "GUEST")
    public void 게스트_Comments_삭제() throws Exception {
        Posts post = postsRepository.save(Posts.builder()
                .title("test post title")
                .user(userRepository.findByEmail("test@test.com").get())
                .content("test post content")
                .build());

        Comments savedComments = commentsRepository.save(Comments.builder()
                .user(userRepository.findByEmail("guest@test.com").get())
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
    public void 사용자_Replies_등록() throws Exception {
        Posts post = postsRepository.save(Posts.builder()
                .title("test post title")
                .user(userRepository.findByEmail("test@test.com").get())
                .content("test post content")
                .build());

        Comments parents = commentsRepository.save(Comments.builder()
                .user(userRepository.findByEmail("test@test.com").get())
                .body("parents body")
                .posts(post)
                .build());

        RepliesSaveRequestDto children = RepliesSaveRequestDto.builder()
                .user(userRepository.findByEmail("test@test.com").get())
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

    @Test
    @WithMockUser(roles = "GUEST")
    public void 게스트_Replies_등록() throws Exception {
        Posts post = postsRepository.save(Posts.builder()
                .title("test post title")
                .user(userRepository.findByEmail("test@test.com").get())
                .content("test post content")
                .build());

        Comments parents = commentsRepository.save(Comments.builder()
                .user(userRepository.findByEmail("test@test.com").get())
                .body("parents body")
                .posts(post)
                .build());

        RepliesSaveRequestDto children = RepliesSaveRequestDto.builder()
                .user(userRepository.findByEmail("guest@test.com").get())
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
