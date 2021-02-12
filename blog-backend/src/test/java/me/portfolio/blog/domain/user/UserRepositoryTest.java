package me.portfolio.blog.domain.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void 회원_저장_조회() {
        userRepository.save(User.builder()
                .name("test user")
                .email("user@test.com")
                .picture("/images/default.png")
                .role(Role.USER)
                .build());
        userRepository.save(User.builder()
                .name("guest user")
                .email("guest@test.com")
                .picture("/images/default.png")
                .role(Role.GUEST)
                .build());

        User user = userRepository.findByEmail("user@test.com").get();
        User guestUser = userRepository.findByEmail("guest@test.com").get();

        assertEquals(user.getEmail(), "user@test.com");
        assertEquals(user.getRole(), Role.USER);

        assertThat(guestUser.getEmail().equals("guest@test.com"));
        assertThat(guestUser.getRole().equals(Role.GUEST));
    }
}
