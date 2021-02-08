package me.portfolio.blog.domain.comments;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.portfolio.blog.domain.posts.Posts;
import org.springframework.stereotype.Repository;

import java.util.List;

import static me.portpolio.blog.domain.comments.QComments.comments;

@RequiredArgsConstructor
@Repository
public class CommentsQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Comments> findByPosts(Posts posts){
        return queryFactory.selectFrom(comments)
                .where(comments.posts.eq(posts))
                .fetch();
    }
    
    //대댓글 조회 테스트 때문에 만듬
    public List<Comments> findByParents(Comments parent){
        return queryFactory.selectFrom(comments)
                .where(comments.parents.eq(parent))
                .fetch();
    }
}
