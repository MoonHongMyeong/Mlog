package me.portfolio.blog.domain.comments;

import com.querydsl.jpa.impl.JPAQueryFactory;
import me.portfolio.blog.domain.posts.Posts;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static me.portfolio.blog.domain.comments.QComments.comments;

@Repository
public class CommentsRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public CommentsRepositorySupport(JPAQueryFactory queryFactory) {
        super(Comments.class);
        this.queryFactory = queryFactory;
    }

    public List<Comments> findByPosts(Posts posts) {
        return queryFactory.selectFrom(comments)
                .where(comments.posts.eq(posts))
                .fetch();
    }

    public List<Comments> findByParents(Comments parent) {
        return queryFactory.selectFrom(comments)
                .where(comments.parents.eq(parent))
                .fetch();
    }
}
