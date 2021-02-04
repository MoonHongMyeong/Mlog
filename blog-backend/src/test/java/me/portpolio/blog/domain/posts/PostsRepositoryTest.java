package me.portpolio.blog.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @Autowired
    PostsRepositorySupport postsRepositorySupport;

    @Autowired
    PostsQueryRepository postsQueryRepository;

    @AfterEach
    public void cleanup(){
        postsRepository.deleteAll();
    }

    //passed
    @Test
    public void 포스트_저장_조회(){
        String title = "Test title";
        String content = "Test content";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("test@test.com")
                .build());

        List<Posts> postsList = postsRepository.findAll();
        Posts posts = postsList.get(0);

        assertEquals(posts.getTitle(), title);
        assertEquals(posts.getContent(), content);
    }


    //passed
    @Test
    public void BaseTimeEntity_등록(){
        LocalDateTime now = LocalDateTime.of(2020,1,4,0,0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>> createDate="+posts.getCreatedDate()+", modifiedDate="+posts.getModifiedDate());

        //isAtLeast == isAfter, isAtMost == isBefore
        assertThat(posts.getCreatedDate()).isAtLeast(now);
        assertThat(posts.getModifiedDate()).isAtLeast(now);

    }

    //passed
    @Test
    public void 포스트_목록_조회(){
        for(int i=1; i<=10 ; i++){
            postsRepository.save(Posts.builder()
                    .title("title"+i)
                    .content("content"+i)
                    .author("author"+i)
                    .build());
        }

        List<Posts> postsList = postsRepository.findAll();

        for(int i=0; i<10; i++){
            assertEquals(postsList.get(i).getTitle(), "title"+(i+1));
            assertEquals(postsList.get(i).getContent(), "content"+(i+1));
            assertEquals(postsList.get(i).getAuthor(), "author"+(i+1));
        }
    }

    //passed
    @Test
    public void 검색_조회_기능(){
        String title = "테스트 제목";
        String content = "테스트 내용";
        String author = "테스트 작성자";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        List<Posts> result = postsRepositorySupport.findByTitle("테스트");

        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getTitle(),title);
        assertEquals(result.get(0).getContent(), content);
        assertEquals(result.get(0).getAuthor(),author);
    }

    //passed
    @Test
    public void querydsl_custom_설정_기능_확인(){
        String title = "테스트 제목";
        String content = "테스트 내용";
        String author = "테스트 작성자";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        List<Posts> result = postsRepository.findByTitle(title);

        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getTitle(),title);
    }

    //passed
    @Test
    public void querydsl_queryRepository_기능_확인(){
        String title = "테스트 제목";
        String content = "테스트 내용";
        String author = "테스트 작성자";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        List<Posts> result = postsQueryRepository.findByTitle(title);

        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getTitle(),title);
    }

    @Test
    public void Posts_검색_기능(){
        String title = "test title";
        String content = "test content";
        String author = "test author";
        String dummny = "dummy title";

        //검색 받을 2개의 데이터와 2개의 더미를 insert
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());
        postsRepository.save(Posts.builder()
                .title(dummny)
                .content(content)
                .author(author)
                .build());
        postsRepository.save(Posts.builder()
                .title(dummny)
                .content(content)
                .author(author)
                .build());
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        List<Posts> postsList = postsRepositorySupport.findByTitle("test");

        Posts posts1 = postsList.get(0);
        Posts posts2 = postsList.get(1);

        assertEquals(postsList.size(), 2);
    }

}
