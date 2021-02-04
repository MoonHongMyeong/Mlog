package me.portpolio.blog.domain.posts;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import static me.portpolio.blog.domain.posts.QPosts.posts;

@RequiredArgsConstructor
@Repository
public class PostsQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Posts> findByTitle(String title){
        return queryFactory.selectFrom(posts)
                .where(posts.title.eq(title))
                .fetch();
    }

    public List<Posts> findAllDesc(){
        return queryFactory.selectFrom(posts)
                .orderBy(posts.id.desc())
                .fetch();
    }
}
