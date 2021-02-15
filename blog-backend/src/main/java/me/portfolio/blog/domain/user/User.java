package me.portfolio.blog.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portfolio.blog.domain.BaseTimeEntity;
import me.portfolio.blog.domain.categories.Categories;
import me.portfolio.blog.domain.comments.Comments;
import me.portfolio.blog.domain.like.LikeVal;
import me.portfolio.blog.domain.posts.Posts;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @JsonIgnore
    @OneToMany(targetEntity = Categories.class ,mappedBy = "user", cascade = CascadeType.ALL)
    private List<Categories> categoriesList;

    @JsonIgnore
    @OneToMany(targetEntity = Posts.class, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Posts> postsList;

    @JsonIgnore
    @OneToMany(targetEntity = Comments.class, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comments> commentsList;

    @JsonIgnore
    @OneToMany(targetEntity = LikeVal.class, mappedBy = "user", cascade = CascadeType.ALL)
    private List<LikeVal> likeValList;

    @Builder
    public User(String name, String email, String picture, Role role){
        this.name=name;
        this.email=email;
        this.picture=picture;
        this.role=role;
    }

    public User update(String name, String picture){
        this.name=name;
        this.picture=picture;

        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}
