package me.portpolio.blog;

import me.portpolio.blog.domain.comments.Comments;
import me.portpolio.blog.domain.comments.CommentsRepository;
import me.portpolio.blog.domain.posts.Posts;
import me.portpolio.blog.domain.posts.PostsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
public class TestDataInput {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    @Test
    public void 테스트_데이터_등록(){
        for (int i=1; i<=10; i++) {
            Posts posts = postsRepository.save(Posts.builder()
                    .title("test posts title"+i)
                    .content("test posts content"+i)
                    .author("test posts author"+i)
                    .imageUrl("./images/default.jpg")
                    .build());
        }
        
        for(int i=0; i<10; i++){
            List<Posts> postsList = postsRepository.findAll();
            Posts posts = postsList.get(i); 
            for(int j=1; j<=10; j++){
                Comments comments = commentsRepository.save(Comments.builder()
                        .author("test comments author"+j)
                        .body("test comments body"+j)
                        .posts(posts)
                        .build());
            }
        }

    }

}
