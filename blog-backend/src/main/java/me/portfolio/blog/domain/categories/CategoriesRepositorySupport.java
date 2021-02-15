package me.portfolio.blog.domain.categories;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static me.portfolio.blog.domain.categories.QCategories.categories;
@Repository
public class CategoriesRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public CategoriesRepositorySupport(JPAQueryFactory queryFactory){
        super(Categories.class);
        this.queryFactory = queryFactory;
    }
    //해당 유저의 카테고리 목록과 그 카테고리 안에 있는 포스트들의 갯수 구하기
    public List<Categories> findByUserId(Long userId){
        return queryFactory.selectFrom(categories)
                .where(categories.user.id.eq(userId))
                .fetch();
    }
    //해당 유저의 카테고리 이름 검색
    public Categories findByUserAndName(Long userId, String categoryName) {
        return queryFactory.selectFrom(categories)
                .where(categories.user.id.eq(userId), categories.name.eq(categoryName))
                .fetchOne();
    }

}
