package me.portfolio.blog.domain.categories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portfolio.blog.domain.BaseTimeEntity;
import me.portfolio.blog.domain.posts.Posts;
import me.portfolio.blog.domain.user.User;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Categories extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categories_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    // xxx 유저의 카테고리 구분짓기 위해서
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "categories", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Posts> postsInCategory;

    @Builder
    public Categories(String name, User user){
        this.name=name;
        this.user=user;
    }

    public void update(String name){
        this.name=name;
    }
}
