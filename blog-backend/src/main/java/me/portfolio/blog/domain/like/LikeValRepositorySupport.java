package me.portfolio.blog.domain.like;

import com.querydsl.jpa.impl.JPAQueryFactory;
import me.portfolio.blog.domain.posts.Posts;
import me.portfolio.blog.domain.user.User;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static me.portfolio.blog.domain.like.QLikeVal.likeVal;

@Repository
public class LikeValRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public LikeValRepositorySupport(JPAQueryFactory queryFactory) {
        super(LikeVal.class);
        this.queryFactory = queryFactory;
    }

    public List<LikeVal> postsCountLike(Posts post){
        return queryFactory.selectFrom(likeVal)
                .where(likeVal.posts.eq(post))
                .fetch();
    }

    public LikeVal checkLikeWithUser(User user, Posts post){
        return queryFactory.selectFrom(likeVal)
                .where(likeVal.posts.eq(post),
                        likeVal.user.eq(user))
                .fetchOne();
    }
}
