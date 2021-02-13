package me.portfolio.blog.domain.posts;

import me.portfolio.blog.domain.user.Role;
import me.portfolio.blog.domain.user.User;
import me.portfolio.blog.domain.user.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("local")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostsRepositoryTest {
    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private PostsRepositorySupport postsRepositorySupport;

    @Autowired
    private PostsQueryRepository postsQueryRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterAll
    public void cleanup(){
        List<User> userList = userRepository.findAll();
        User testUser = userList.get((userList.size()-1));
        userRepository.deleteById(testUser.getId());
        System.out.println("===========delete user===============");
    }

    @BeforeAll
    public void setUser(){
        User testUser = userRepository.save(User.builder()
                .email("test@test.com")
                .name("test user")
                .picture("/images/default")
                .role(Role.USER).build());

        System.out.println("==============insert user================");
    }
    //passed
    @Test
    public void 포스트_저장_조회(){
        String title = "Test title";
        String content = "Test content";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .user(userRepository.findByEmail("test@test.com").get())
                .build());

        List<Posts> postsList = postsRepository.findAll();
        Posts posts = postsList.get((postsList.size()-1));

        assertEquals(posts.getTitle(), title);
        assertEquals(posts.getContent(), content);

        postsRepository.deleteById(posts.getId());
    }


    //passed
    @Test
    public void BaseTimeEntity_등록(){
        LocalDateTime now = LocalDateTime.of(2020,1,4,0,0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .user(userRepository.findByEmail("test@test.com").get())
                .build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get((postsList.size()-1));

        System.out.println(">>>>>>>> createDate="+posts.getCreatedDate()+", modifiedDate="+posts.getModifiedDate());

        //isAtLeast == isAfter, isAtMost == isBefore
        assertThat(posts.getCreatedDate()).isAtLeast(now);
        assertThat(posts.getModifiedDate()).isAtLeast(now);

        postsRepository.deleteById(posts.getId());
    }

    //passed
    @Test
    public void 포스트_목록_조회() {
        User user = userRepository.findByEmail("test@test.com").get();
        System.out.println("================get user=====================");
        for (int i = 1; i <= 5; i++) {
            postsRepository.save(Posts.builder()
                    .title("title")
                    .content("content")
                    .user(user)
                    .build());
            System.out.println("=============insert posts"+i+"=================");
        }
        List<Posts> postsList = postsRepository.findAll();
        System.out.println("=================call list==================");

        for (int i = postsList.size() - 1; i > postsList.size() - 6; i--) {
            assertEquals(postsList.get(i).getTitle(), "title");
            assertEquals(postsList.get(i).getContent(), "content");
            System.out.println("=======================check post"+i+"=====================");
        }

        for (int i = postsList.size() - 1; i > postsList.size() - 6; i--) {
            Posts post = postsList.get(i);
            postsRepository.deleteById(post.getId());
            System.out.println("=================delete post"+i+"=======================");
        }
    }

    //passed
    @Test
    public void 검색_조회_기능(){
        String title = "절대따라할수없는테스트 제목";
        String content = "테스트 내용";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .user(userRepository.findByEmail("test@test.com").get())
                .build());

        List<Posts> result = postsRepositorySupport.findByTitle(title);

        assertEquals(result.size(), 1);
        assertEquals(result.get((result.size()-1)).getTitle(),title);
        assertEquals(result.get((result.size()-1)).getContent(), content);

        postsRepository.deleteById(result.get((result.size()-1)).getId());
    }

    //passed
    @Test
    public void querydsl_custom_설정_기능_확인(){
        String title = "절대따라할수없는테스트 제목";
        String content = "테스트 내용";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .user(userRepository.findByEmail("test@test.com").get())
                .build());

        List<Posts> result = postsRepository.findByTitle(title);

        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getTitle(),title);

        postsRepository.deleteById(result.get(0).getId());
    }

    //passed
    @Test
    public void querydsl_queryRepository_기능_확인(){
        String title = "절대따라할수없는테스트 제목";
        String content = "테스트 내용";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .user(userRepository.findByEmail("test@test.com").get())
                .build());

        List<Posts> result = postsQueryRepository.findByTitle(title);

        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getTitle(),title);

        postsRepository.deleteById(result.get(0).getId());
    }
}
