package me.portfolio.blog.domain.posts;

import com.querydsl.jpa.impl.JPAQueryFactory;
import me.portfolio.blog.domain.like.LikeVal;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static me.portfolio.blog.domain.posts.QPosts.posts;

@Repository
public class PostsRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public PostsRepositorySupport(JPAQueryFactory queryFactory) {
        super(Posts.class);
        this.queryFactory = queryFactory;
    }
    //정렬 최근 순
    public List<Posts> findAllDesc(){
        return queryFactory.selectFrom(posts)
                .orderBy(posts.id.desc())
                .fetch();
    }
    //검색 하는데 정렬 최근 순
    public List<Posts> findByTitle(String title){
        return queryFactory.selectFrom(posts)
                .where(posts.title.contains((title)))
                .orderBy(posts.id.desc())
                .fetch();
    }
}
