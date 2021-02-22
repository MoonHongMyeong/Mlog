package me.portfolio.blog.domain.comments;

import me.portfolio.blog.domain.categories.Categories;
import me.portfolio.blog.domain.categories.CategoriesRepository;
import me.portfolio.blog.domain.posts.Posts;
import me.portfolio.blog.domain.posts.PostsRepository;
import me.portfolio.blog.domain.user.Role;
import me.portfolio.blog.domain.user.User;
import me.portfolio.blog.domain.user.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommentsRepositoryTest {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentsQueryRepository commentsQueryRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @AfterAll
    public void cleanup(){
        List<User> userList = userRepository.findAll();
        User testUser = userList.get((userList.size()-1));
        userRepository.deleteById(testUser.getId());
    }

    @BeforeAll
    public void setUser(){
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
                .temp("Y")
                .build());
    }
    //passed
    @Test
    public void 댓글_저장_조회(){
        String body = "test comments body";

        LocalDateTime now = LocalDateTime.of(2021,2,7,0,0,0);

        List<Posts> postsList = postsRepository.findAll();
        Posts post = postsList.get((postsList.size()-1));

        commentsRepository.save(Comments.builder()
                .posts(post)
                .body(body)
                .user(userRepository.findByEmail("test@test.com").get())
                .build());

        List<Comments> commentsList = commentsRepository.findAll();
        Comments comments = commentsList.get((commentsList.size() - 1));

        assertEquals(comments.getPosts().getId(), post.getId());
        assertEquals(comments.getBody(), body);
        assertEquals(comments.getUser().getEmail(), "test@test.com");
        assertThat(comments.getCreatedDate()).isAtLeast(now);
        assertThat(comments.getModifiedDate()).isAtLeast(now);

    }

    //passed
    @Test
    public void 댓글_목록_조회(){
        List<Posts> postsList = postsRepository.findAll();
        Posts post = postsList.get((postsList.size()-1));

        for(int i=1; i<=10; i++) {
            commentsRepository.save(Comments.builder()
                    .posts(post)
                    .body("test body")
                    .user(userRepository.findByEmail("test@test.com").get())
                    .build());
        }

        List<Comments> commentsList = commentsRepository.findAll();

        for(int i=commentsList.size()-1 ; i>commentsList.size()-11; i--){
            assertEquals(commentsList.get(i).getBody(), "test body");
            assertEquals(commentsList.get(i).getUser().getEmail(), "test@test.com");

        }

    }

    //passed
    @Test
    public void 대댓글_저장_조회(){
        String body = "test comments body";
        String author = "test comments author";
        LocalDateTime now = LocalDateTime.of(2020, 02, 07, 0,0,0);

        List<Posts> postsList = postsRepository.findAll();
        Posts post = postsList.get((postsList.size()-1));

        Comments comments = commentsRepository.save(Comments.builder()
                .posts(post)
                .body(body)
                .user(userRepository.findByEmail("test@test.com").get())
                .build());

        commentsRepository.save(Comments.builder()
                .posts(post)
                .parents(comments)
                .user(userRepository.findByEmail("test@test.com").get())
                .body("test replies body")
                .build());

        List<Comments> commentsList = commentsRepository.findAll();
        Comments parents = commentsList.get((commentsList.size()-2));
        assertEquals(parents.getPosts().getId(), post.getId());
        assertEquals(parents.getBody(), body);
        assertEquals(parents.getUser().getEmail(), "test@test.com");
        assertThat(parents.getCreatedDate()).isAtLeast(now);
        assertThat(parents.getModifiedDate()).isAtLeast(now);

        List<Comments> children = commentsQueryRepository.findByParents(comments);
        assertEquals(children.get((children.size()-1)).getPosts().getId(), parents.getPosts().getId());
        assertEquals(children.get((children.size()-1)).getBody(), "test replies body");
        assertEquals(children.get((children.size()-1)).getUser().getEmail(), "test@test.com");
        assertEquals(children.get((children.size()-1)).getParents().getId(), parents.getId());
        assertThat(children.get((children.size()-1)).getModifiedDate()).isAtLeast(now);

    }

}
