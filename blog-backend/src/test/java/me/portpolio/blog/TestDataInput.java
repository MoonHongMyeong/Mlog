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
        for (int i=1; i<=100; i++) {
            Posts posts = postsRepository.save(Posts.builder()
                    .title("포스트 테스트 제목"+i)
                    .content("포스트 테스트 내용"+i)
                    .author("포스트 테스트 작성자"+i)
                    .imageUrl("/images/default.png")
                    .build());
        }
        
        for(int i=0; i<100; i++){
            List<Posts> postsList = postsRepository.findAll();
            Posts posts = postsList.get(i); 
            for(int j=1; j<=2; j++){
                Comments comments = commentsRepository.save(Comments.builder()
                        .author("1차댓글 작성자"+j)
                        .body("1차댓글 작성 내용"+j)
                        .posts(posts)
                        .build());
            }
        }

    }

}
