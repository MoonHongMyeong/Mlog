package me.portfolio.blog.web;

import me.portfolio.blog.config.auth.dto.SessionUser;
import me.portfolio.blog.domain.categories.Categories;
import me.portfolio.blog.domain.categories.CategoriesRepository;
import me.portfolio.blog.domain.like.LikeVal;
import me.portfolio.blog.domain.like.LikeValRepository;
import me.portfolio.blog.domain.like.LikeValRepositorySupport;
import me.portfolio.blog.domain.posts.Posts;
import me.portfolio.blog.domain.posts.PostsRepository;
import me.portfolio.blog.domain.user.Role;
import me.portfolio.blog.domain.user.User;
import me.portfolio.blog.domain.user.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostLikeApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private LikeValRepository likeValRepository;
    @Autowired
    private LikeValRepositorySupport likeValRepositorySupport;

    private MockMvc mvc;

    @AfterAll
    public void cleanup() {
        User user1 = userRepository.findByEmail("testUser@test.com").get();
        User user2 = userRepository.findByEmail("guestUser@test.com").get();
        userRepository.delete(user1);
        userRepository.delete(user2);
    }

    @BeforeAll
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        User postUser = userRepository.save(User.builder()
                .email("testUser@test.com")
                .picture("testUsersPicture.png")
                .name("testUser")
                .role(Role.USER)
                .build());

        User testUser = userRepository.save(User.builder()
                .email("guestUser@test.com")
                .picture("guestUsersPicture.png")
                .name("guestUser")
                .role(Role.GUEST)
                .build());

        Categories categories = categoriesRepository.save(Categories.builder()
                .user(postUser)
                .name("testCategories")
                .build());

        Posts posts = postsRepository.save(Posts.builder()
                .likeCount(0)
                .title("testPost")
                .content("testPost")
                .categories(categories)
                .imageUrl("testPost.png")
                .user(postUser)
                .temp("Y")
                .build());
    }

    @Test
    public void Posts_좋아요_기능() throws Exception {
        User testUser = userRepository.findByEmail("guestUser@test.com").get();
        List<Posts> postsList = postsRepository.findAll();
        Posts post = postsList.get((postsList.size() - 1));

        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("user", new SessionUser(testUser));

        String addCountUrl = "http://localhost:" + port + "/api/v2/posts/" + post.getId() + "/like";
        MvcResult result = mvc.perform(get(addCountUrl)
                .session(mockHttpSession))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), "1");
    }

    @Test
    public void Posts_좋아요_취소_기능() throws Exception {
        User testUser = userRepository.findByEmail("guestUser@test.com").get();
        List<Posts> postsList = postsRepository.findAll();
        Posts post = postsList.get((postsList.size() - 1));

        //취소를 하기 위해서는 테이블에 있어야 함
        likeValRepository.save(LikeVal.builder()
                .posts(post)
                .user(testUser)
                .build());

        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("user", new SessionUser(testUser));

        String minusCountUrl = "http://localhost:" + port + "/api/v2/posts/" + post.getId() + "/disLike";
        MvcResult result = mvc.perform(get(minusCountUrl)
                .session(mockHttpSession))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), "0");
        //개별 테스트떄는 -1이어야함 위에서 안올라가있기 때문
//        assertEquals(result.getResponse().getContentAsString(), "-1");
    }

}
