package me.portfolio.blog.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.portfolio.blog.config.auth.dto.SessionUser;
import me.portfolio.blog.domain.categories.Categories;
import me.portfolio.blog.domain.categories.CategoriesRepository;
import me.portfolio.blog.domain.categories.CategoriesRepositorySupport;
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

import java.util.List;

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

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private CategoriesRepositorySupport categoriesRepositorySupport;

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
        Categories userCategory = categoriesRepository.save(Categories.builder()
                .name("testUserCategory")
                .user(testUser)
                .build());
        System.out.println("=============save testUser Category ================");
        Posts post = postsRepository.save(Posts.builder()
                .title("test post title")
                .user(testUser)
                .content("test post content")
                .categories(userCategory)
                .build());
        System.out.println("================save posts=======================");
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 사용자_Comments_등록() throws Exception {
        List<User> userList = userRepository.findAll();
        User user = userList.get((userList.size()-2));
        System.out.println("=======find user========");
        List<Posts> postsList = postsRepository.findAll();
        Posts post = postsList.get((postsList.size())-1);
        System.out.println("=========find post=======");
        CommentsSaveRequestDto requestDto = CommentsSaveRequestDto.builder()
                .body("test comment body")
                .build();

        String url = "http://localhost:" + port + "/api/v2/posts/" + post.getId() + "/comments";
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", new SessionUser(user));

        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .session(session)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(roles = "GUEST")
    public void 게스트_Comments_등록() throws Exception {
        List<User> userList = userRepository.findAll();
        User guestUser = userList.get((userList.size()-2));
        List<Posts> postsList = postsRepository.findAll();
        Posts post = postsList.get((postsList.size())-1);

        CommentsSaveRequestDto requestDto = CommentsSaveRequestDto.builder()
                .body("test comment body")
                .build();

        String url = "http://localhost:" + port + "/api/v2/posts/" + post.getId() + "/comments";

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", new SessionUser(guestUser));

        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .session(session)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(roles = "USER")
    public void 사용자_Comments_수정() throws Exception {
        User user = userRepository.findByEmail("test@test.com").get();
        Posts post = postsRepository.findByTitle("test post title").get(0);

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

    }

    @Test
    @WithMockUser(roles = "GUEST")
    public void 게스트_Comments_수정() throws Exception {
        User guestUser = userRepository.findByEmail("guest@test.com").get();
        Posts post = postsRepository.findByTitle("test post title").get(0);

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

    }

    @Test
    @WithMockUser(roles = "USER")
    public void 사용자_Comments_삭제() throws Exception {
        User user = userRepository.findByEmail("test@test.com").get();
        Posts post = postsRepository.findByTitle("test post title").get(0);

        Comments savedComments = commentsRepository.save(Comments.builder()
                .user(user)
                .body("body")
                .posts(post)
                .build());

        Long id = savedComments.getId();
        String url = "http://localhost:" + port + "/api/v2/posts/" + post.getId() + "/comments/" + id;

        mvc.perform(delete(url)).andExpect(status().isOk());

    }

    @Test
    @WithMockUser(roles = "GUEST")
    public void 게스트_Comments_삭제() throws Exception {
        User guestUser = userRepository.findByEmail("guest@test.com").get();
        Posts post = postsRepository.findByTitle("test post title").get(0);
        Comments savedComments = commentsRepository.save(Comments.builder()
                .user(guestUser)
                .body("body")
                .posts(post)
                .build());

        Long id = savedComments.getId();
        String url = "http://localhost:" + port + "/api/v2/posts/" + post.getId() + "/comments/" + id;

        mvc.perform(delete(url)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 사용자_Replies_등록() throws Exception {
        List<User> userList = userRepository.findAll();
        User user = userList.get((userList.size()-2));
        List<Posts> postsList = postsRepository.findAll();
        Posts post = postsList.get((postsList.size())-1);

        Comments parents = commentsRepository.save(Comments.builder()
                .user(user)
                .body("parents body")
                .posts(post)
                .build());

        RepliesSaveRequestDto children = RepliesSaveRequestDto.builder()
                .body("children body")
                .build();

        Long parent_id = parents.getId();
        String url = "http://localhost:" + port + "api/v2/posts/" + post.getId() + "/comments/" + parent_id;

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", new SessionUser(user));

        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .session(session)
                .content(new ObjectMapper().writeValueAsString(children)))
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(roles = "GUEST")
    public void 게스트_Replies_등록() throws Exception {
        List<User> userList = userRepository.findAll();
        User guestUser = userList.get((userList.size()-1));
        List<Posts> postsList = postsRepository.findAll();
        Posts post = postsList.get((postsList.size())-1);

        Comments parents = commentsRepository.save(Comments.builder()
                .user(guestUser)
                .body("parents body")
                .posts(post)
                .build());

        RepliesSaveRequestDto children = RepliesSaveRequestDto.builder()
                .body("children body")
                .build();

        Long parent_id = parents.getId();
        String url = "http://localhost:" + port + "api/v2/posts/" + post.getId() + "/comments/" + parent_id;

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", new SessionUser(guestUser));

        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .session(session)
                .content(new ObjectMapper().writeValueAsString(children)))
                .andExpect(status().is(200));

    }

}
