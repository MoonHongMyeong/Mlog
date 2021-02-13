package me.portfolio.blog.domain.categories;

import me.portfolio.blog.domain.user.Role;
import me.portfolio.blog.domain.user.User;
import me.portfolio.blog.domain.user.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("local")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class CategoriesRepositoryTest {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private UserRepository userRepository;

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
    }

    @Test
    public void 카테고리_저장_조회(){
        User testUser = userRepository.findByEmail("test@test.com").get();
        String name = "test category";
        categoriesRepository.save(Categories.builder()
                .name(name)
                .user(testUser)
                .build());
        categoriesRepository.save(Categories.builder()
                .name("test2category")
                .user(testUser)
                .build());
        List<Categories> categoryList = categoriesRepository.findAll();
        Categories category1 = categoryList.get((categoryList.size()-2));
        Categories category2 = categoryList.get((categoryList.size()-1));

        assertEquals(category1.getName(), name);
        assertEquals(category2.getName(),"test2category");

        categoriesRepository.delete(category1);
        categoriesRepository.delete(category2);
    }
}
