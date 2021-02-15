package me.portfolio.blog.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.portfolio.blog.config.auth.dto.SessionUser;
import me.portfolio.blog.domain.categories.Categories;
import me.portfolio.blog.domain.categories.CategoriesRepository;
import me.portfolio.blog.domain.categories.CategoriesRepositorySupport;
import me.portfolio.blog.domain.user.Role;
import me.portfolio.blog.domain.user.User;
import me.portfolio.blog.domain.user.UserRepository;
import me.portfolio.blog.web.dto.categories.CategoriesSaveRequestDto;
import me.portfolio.blog.web.dto.categories.CategoriesUpdateRequestDto;
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
import org.springframework.http.MediaType;
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
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoriesApiControllerTest {
    @LocalServerPort
    private int port;

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
    public void cleanup() {
        List<User> userList = userRepository.findAll();
        User testUser = userList.get((userList.size() - 1));
        userRepository.deleteById(testUser.getId());
    }

    @BeforeAll
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

    @Test
    @WithMockUser(roles = "USER")
    public void 카데고리_등록() throws Exception {
        User user = userRepository.findByEmail("test@test.com").get();

        CategoriesSaveRequestDto requestDto = CategoriesSaveRequestDto.builder()
                .name("test category")
                .user(user)
                .build();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", new SessionUser(user));

        String url = "http://localhost:" + port + "/api/v2/user/" + user.getId() + "/categories";

        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .session(session).content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<Categories> categories = categoriesRepository.findAll();
        Categories category = categories.get((categories.size()-1));

        assertEquals(category.getName(), "test category");
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 카테고리_수정() throws Exception {
        User user = userRepository.findByEmail("test@test.com").get();

        Categories savedCategories = categoriesRepository.save(Categories.builder()
                .user(user)
                .name("바뀌기전카테고리명")
                .build());

        String exceptedName = "exceptedCategoryName";

        CategoriesUpdateRequestDto requestDto = CategoriesUpdateRequestDto.builder()
                .name(exceptedName)
                .build();

        String url = "http://localhost:" + port + "/api/v2/user/" + user.getId() + "/categories/" + savedCategories.getId();

        mvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<Categories> categories = categoriesRepository.findAll();
        Categories category = categories.get((categories.size()-1));

        assertEquals(category.getName(), exceptedName);

    }

    @Test
    @WithMockUser(roles = "USER")
    public void 카테고리_삭제() throws Exception {
        User user = userRepository.findByEmail("test@test.com").get();

        Categories savedCategories = categoriesRepository.save(Categories.builder()
                .name("삭제테스트카테고리내용")
                .user(user)
                .build());

        String url = "http://localhost:" + port + "/api/v2/user/" + user.getId() + "/categories/" + savedCategories.getId();

        mvc.perform(delete(url)).andExpect(status().isOk());
    }
}
