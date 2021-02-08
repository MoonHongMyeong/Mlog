package me.portfolio.blog.domain.posts;

import java.util.List;

public interface PostsRepositoryCustom {
    List<Posts> findByTitle(String title);
}
