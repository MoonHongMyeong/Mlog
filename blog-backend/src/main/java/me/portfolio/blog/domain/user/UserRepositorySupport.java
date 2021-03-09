package me.portfolio.blog.domain.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import me.portfolio.blog.config.auth.dto.SessionUser;
import me.portfolio.blog.domain.categories.Categories;
import me.portfolio.blog.domain.comments.Comments;
import me.portfolio.blog.domain.posts.Posts;
import me.portfolio.blog.web.dto.posts.PostsListResponseDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static me.portfolio.blog.domain.categories.QCategories.categories;
import static me.portfolio.blog.domain.posts.QPosts.posts;
import static me.portfolio.blog.domain.comments.QComments.comments;
import static me.portfolio.blog.domain.user.QUser.user;
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
    //해당 유저의 카테고리 목록 조회
    public List<Categories> CategoriesByUser(Long userId) {
        return queryFactory.selectFrom(categories)
                .where(categories.user.id.eq(userId))
                .fetch();
    }
    //현재 접속해있는 유저 아이디 가져오기
    public User getLoginUser(SessionUser sessionUser) {
        return queryFactory.selectFrom(user)
                .where(user.email.eq(sessionUser.getEmail()))
                .fetchOne();
    }

    public List<Posts> PostsByUserWithCategories(Long userId, Long categoryId) {
        return queryFactory.selectFrom(posts).where(posts.user.id.eq(userId), posts.categories.id.eq(categoryId)).fetch();
    }
}
