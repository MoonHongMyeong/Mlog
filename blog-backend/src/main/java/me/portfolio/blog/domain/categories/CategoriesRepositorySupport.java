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
    public List<Categories> findCategories(Long userId){
        return queryFactory.select(Projections.constructor(Categories.class, categories.name, categories.postsInCategory))
                .from(categories)
                .where(categories.user.id.eq(userId))
                .fetch();
    }

    //유저 가입(등록)시 자동으로 카테고리에 "일반"이라는 카테고리명으로 테이블에 넣기 이걸 여기서 해야되나? UserRepositorySupport 를 만들어서 쿼리를 같이 쏴주는게 맞지않나?
    //feature-user에서 해결하기로 함
}
