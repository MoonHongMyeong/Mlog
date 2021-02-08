package me.portfolio.blog.domain.posts;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import static me.portpolio.blog.domain.posts.QPosts.posts;
@RequiredArgsConstructor
public class PostsRepositoryImpl implements PostsRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Posts> findByTitle(String title) {
        return queryFactory.selectFrom(posts)
                .where(posts.title.eq(title))
                .fetch();
    }
}
