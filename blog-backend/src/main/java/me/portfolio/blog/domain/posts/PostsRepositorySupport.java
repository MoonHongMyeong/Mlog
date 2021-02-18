package me.portfolio.blog.domain.posts;

import com.querydsl.jpa.impl.JPAQueryFactory;
import me.portfolio.blog.config.auth.dto.SessionUser;
import me.portfolio.blog.domain.like.LikeVal;
import me.portfolio.blog.web.dto.posts.PostsListResponseDto;
import me.portfolio.blog.web.dto.posts.PostsResponseDto;
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
                .where(posts.temp.eq("Y"))
                .orderBy(posts.id.desc())
                .fetch();
    }
    //검색 하는데 정렬 최근 순
    public List<Posts> findByTitle(String title){
        return queryFactory.selectFrom(posts)
                .where(posts.title.contains((title)), posts.temp.eq("Y"))
                .orderBy(posts.id.desc())
                .fetch();
    }

    //인기 포스트 조회
    public List<Posts> findAllPop() {
        return queryFactory.selectFrom(posts)
                .where(posts.temp.eq("Y"))
                .orderBy(posts.likeCount.desc())
                .fetch();
    }
    //유저의 임시저장 목록 조회
    public List<Posts> findTempPost(SessionUser sessionUser) {
         return queryFactory.selectFrom(posts)
                 .where(posts.user.id.eq(sessionUser.getId()),
                         posts.temp.eq("N"))
                 .fetch();
    }
}
