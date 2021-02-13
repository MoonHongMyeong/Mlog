package me.portfolio.blog.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.portfolio.blog.config.auth.dto.SessionUser;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("local")
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

    @AfterAll
    public void cleanup(){
        User user = userRepository.findByEmail("test@test.com").get();
        User guestUser = userRepository.findByEmail("guest@test.com").get();
        userRepository.delete(user);
        userRepository.delete(guestUser);
    }

    @BeforeAll
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
        System.out.println("=============save testUser===============");
        User guestUser = userRepository.save(User.builder()
                .email("guest@test.com")
                .name("guest user")
                .picture("/images/default")
                .role(Role.GUEST).build());
        System.out.println("============save guestUser===============");
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 사용자_Comments_등록() throws Exception {
        User user = userRepository.findByEmail("test@test.com").get();
        System.out.println("==============get testUser===============");

        Posts post = postsRepository.save(Posts.builder()
                .title("test post title")
                .user(user)
                .content("test post content")
                .build());
        System.out.println("================save posts=======================");
        CommentsSaveRequestDto requestDto = CommentsSaveRequestDto.builder()
                .posts(post)
                .body("test comment body")
                .user(user)
                .build();
        System.out.println("===================requestDto build==================");
        String url = "http://localhost:" + port + "/api/v2/posts/" + post.getId() + "/comments";

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", new SessionUser(user));
        System.out.println("==================set Session+========================");

        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .session(session)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        postsRepository.deleteById(post.getId());
    }

    @Test
    @WithMockUser(roles = "GUEST")
    public void 게스트_Comments_등록() throws Exception {
        User guestUser = userRepository.findByEmail("guest@test.com").get();

        Posts post = postsRepository.save(Posts.builder()
                .title("test post title")
                .user(guestUser)
                .content("test post content")
                .build());

        CommentsSaveRequestDto requestDto = CommentsSaveRequestDto.builder()
                .posts(post)
                .body("test comment body")
                .user(guestUser)
                .build();

        String url = "http://localhost:" + port + "/api/v2/posts/" + post.getId() + "/comments";

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", new SessionUser(guestUser));

        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .session(session)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        postsRepository.deleteById(post.getId());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 사용자_Comments_수정() throws Exception {
        User user = userRepository.findByEmail("test@test.com").get();

        Posts post = postsRepository.save(Posts.builder()
                .title("test post title")
                .user(user)
                .content("test post content")
                .build());

        Comments savedComments = commentsRepository.save(Comments.builder()
                .body("body")
                .user(user)
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
        User guestUser = userRepository.findByEmail("guest@test.com").get();
        Posts post = postsRepository.save(Posts.builder()
                .title("test post title")
                .user(guestUser)
                .content("test post content")
                .build());

        Comments savedComments = commentsRepository.save(Comments.builder()
                .body("body")
                .user(guestUser)
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
        User user = userRepository.findByEmail("test@test.com").get();

        Posts post = postsRepository.save(Posts.builder()
                .title("test post title")
                .user(user)
                .content("test post content")
                .build());

        Comments savedComments = commentsRepository.save(Comments.builder()
                .user(user)
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
        User guestUser = userRepository.findByEmail("guest@test.com").get();
        Posts post = postsRepository.save(Posts.builder()
                .title("test post title")
                .user(guestUser)
                .content("test post content")
                .build());

        Comments savedComments = commentsRepository.save(Comments.builder()
                .user(guestUser)
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
        User user = userRepository.findByEmail("test@test.com").get();

        Posts post = postsRepository.save(Posts.builder()
                .title("test post title")
                .user(user)
                .content("test post content")
                .build());

        Comments parents = commentsRepository.save(Comments.builder()
                .user(user)
                .body("parents body")
                .posts(post)
                .build());

        RepliesSaveRequestDto children = RepliesSaveRequestDto.builder()
                .user(user)
                .body("children body")
                .posts(post)
                .parents(parents)
                .build();

        Long parent_id = parents.getId();
        String url = "http://localhost:" + port + "api/v2/posts/" + post.getId() + "/comments/" + parent_id;

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", new SessionUser(user));

        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .session(session)
                .content(new ObjectMapper().writeValueAsString(children)))
                .andExpect(status().is(200));

        postsRepository.deleteById(post.getId());
    }

    @Test
    @WithMockUser(roles = "GUEST")
    public void 게스트_Replies_등록() throws Exception {
        User guestUser = userRepository.findByEmail("guest@test.com").get();
        Posts post = postsRepository.save(Posts.builder()
                .title("test post title")
                .user(guestUser)
                .content("test post content")
                .build());

        Comments parents = commentsRepository.save(Comments.builder()
                .user(guestUser)
                .body("parents body")
                .posts(post)
                .build());

        RepliesSaveRequestDto children = RepliesSaveRequestDto.builder()
                .user(guestUser)
                .body("children body")
                .posts(post)
                .parents(parents)
                .build();

        Long parent_id = parents.getId();
        String url = "http://localhost:" + port + "api/v2/posts/" + post.getId() + "/comments/" + parent_id;

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", new SessionUser(guestUser));

        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .session(session)
                .content(new ObjectMapper().writeValueAsString(children)))
                .andExpect(status().is(200));

        postsRepository.deleteById(post.getId());

    }

}
