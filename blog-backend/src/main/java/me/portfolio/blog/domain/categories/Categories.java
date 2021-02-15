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

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @JsonIgnore
    @OneToMany(targetEntity = Posts.class, mappedBy = "categories", cascade = CascadeType.ALL)
    private List<Posts> postsList;

    @Builder
    public Categories(String name, User user){
        this.name=name;
        this.user=user;
    }

    public void update(String name){
        this.name=name;
    }
}
