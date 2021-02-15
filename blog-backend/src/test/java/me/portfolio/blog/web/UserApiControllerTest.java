package me.portfolio.blog.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.portfolio.blog.domain.user.Role;
import me.portfolio.blog.domain.user.User;
import me.portfolio.blog.domain.user.UserRepository;
import me.portfolio.blog.web.dto.user.UserUpdateRequestDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mvc;

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

        User guestUser = userRepository.save(User.builder()
                .email("guest@test.com")
                .name("guest user")
                .picture("/images/default")
                .role(Role.GUEST).build());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 사용자_유저_정보_수정() throws Exception {
        String exceptedName = "exceptedName";
        String exceptedPicture = "exceptedPicture";

        User user = userRepository.findByEmail("test@test.com").get();

        UserUpdateRequestDto requestDto = UserUpdateRequestDto.builder()
                .name(exceptedName)
                .picture(exceptedPicture)
                .build();

        String url = "http://localhost:" + port + "/api/v2/user/" + user.getId();

        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "GUEST")
    public void 게스트_유저_정보_수정() throws Exception {
        String exceptedName = "exceptedName";
        String exceptedPicture = "exceptedPicture";

        User guestUser = userRepository.findByEmail("guest@test.com").get();

        UserUpdateRequestDto requestDto = UserUpdateRequestDto.builder()
                .name(exceptedName)
                .picture(exceptedPicture)
                .build();

        String url = "http://localhost:" + port + "/api/v2/user/" + guestUser.getId();

        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(roles = "USER")
    public void 유저_정보_조회() throws Exception{
        User user = userRepository.findByEmail("test@test.com").get();

        String url = "http://localhost:" + port + "/api/v2/user/" + user.getId();

        mvc.perform(get(url)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 유저_사용중_포스트_목록_조회() throws Exception{
        User user = userRepository.findByEmail("test@test.com").get();
        String url = "http://localhost:" + port + "/api/v2/user/" + user.getId()+"/posts";
        mvc.perform(get(url)).andExpect(status().isOk());

    }

    @Test
    @WithMockUser(roles = "USER")
    public void 유저_사용중_댓글_목록_조회() throws Exception{
        User user = userRepository.findByEmail("test@test.com").get();
        String url = "http://localhost:" + port + "/api/v2/user/" + user.getId()+"/comments";
        mvc.perform(get(url)).andExpect(status().isOk());

    }

    @Test
    @WithMockUser(roles = "USER")
    public void 사용자_유저_탈퇴() throws Exception {
        User user = userRepository.findByEmail("test@test.com").get();

        String url = "http://localhost:" + port + "/api/v2/user/" + user.getId();
        mvc.perform(delete(url)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "GUEST")
    public void 게스트_유저_탈퇴() throws Exception {
        User guestUser = userRepository.findByEmail("guest@test.com").get();

        String url = "http://localhost:" + port + "/api/v2/user/" + guestUser.getId();
        mvc.perform(delete(url)).andExpect(status().isOk());
    }
}
