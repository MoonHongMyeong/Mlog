package me.portfolio.blog.domain.like;

import me.portfolio.blog.domain.categories.Categories;
import me.portfolio.blog.domain.categories.CategoriesRepository;
import me.portfolio.blog.domain.posts.Posts;
import me.portfolio.blog.domain.posts.PostsRepository;
import me.portfolio.blog.domain.user.Role;
import me.portfolio.blog.domain.user.User;
import me.portfolio.blog.domain.user.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class LIkeValRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private LikeValRepository likeValRepository;
    @Autowired
    private LikeValRepositorySupport likeValRepositorySupport;

    @AfterAll
    public void cleanup(){
        List<User> userList = userRepository.findAll();
        User testUser = userList.get((userList.size()-1));
        userRepository.delete(testUser);
    }

    @BeforeAll
    public void setup(){
        User testUser = userRepository.save(User.builder()
                .email("test@test.com")
                .name("test user")
                .picture("/images/default")
                .role(Role.USER).build());

        Categories categories = categoriesRepository.save(Categories.builder()
                .user(testUser)
                .name("testCategory")
                .build());

        Posts posts = postsRepository.save(Posts.builder()
                .categories(categories)
                .user(testUser)
                .content("testContentTest")
                .imageUrl("testImageUrl")
                .title("testTitleTest")
                .build());
    }

    @Test
    public void 좋아요_등록_테스트(){
        User user = userRepository.findByEmail("test@test.com").get();
        Posts post = postsRepository.findByTitle("testTitleTest").get(0);

        likeValRepository.save(LikeVal.builder()
                .posts(post)
                .user(user)
                .build());

        List<LikeVal> likeUserList = likeValRepository.findAll();
        LikeVal like = likeUserList.get((likeUserList.size()-1));

        assertEquals(like.getPosts().getId(), post.getId());
        assertEquals(like.getUser().getId(), user.getId());
    }
}
