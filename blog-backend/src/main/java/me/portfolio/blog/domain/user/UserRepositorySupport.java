package me.portfolio.blog.domain.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import me.portfolio.blog.domain.comments.Comments;
import me.portfolio.blog.domain.posts.Posts;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static me.portfolio.blog.domain.posts.QPosts.posts;
import static me.portfolio.blog.domain.comments.QComments.comments;
@Repository
public class UserRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public UserRepositorySupport(JPAQueryFactory queryFactory) {
        super(User.class);
        this.queryFactory=queryFactory;
    }

    //해당 유저의 포스트 목록 조회
    public List<Posts> PostsByUser(Long userId) {
        return queryFactory.selectFrom(posts)
                .where(posts.user.id.eq(userId))
                .fetch();
    }

    //해당 유저의 댓글 목록 조회
    public List<Comments> CommentsByUser(Long userId) {
        return queryFactory.selectFrom(comments)
                .where(comments.user.id.eq(userId))
                .fetch();
    }

}
