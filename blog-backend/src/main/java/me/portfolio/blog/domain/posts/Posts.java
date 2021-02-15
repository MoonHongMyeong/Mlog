package me.portfolio.blog.domain.posts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portfolio.blog.domain.BaseTimeEntity;
import me.portfolio.blog.domain.categories.Categories;
import me.portfolio.blog.domain.comments.Comments;
import me.portfolio.blog.domain.like.LikeVal;
import me.portfolio.blog.domain.user.User;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "posts_id")
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "varchar(255) default '/images/default.jpg'")
    private String imageUrl;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int likeCount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="categories_id", nullable = false)
    private Categories categories;

    @JsonIgnore
    @OneToMany(targetEntity = Comments.class, mappedBy = "posts", cascade = CascadeType.ALL)
    private List<Comments> commentsList;

    @JsonIgnore
    @OneToMany(targetEntity = LikeVal.class, mappedBy = "posts", cascade = CascadeType.ALL)
    private List<LikeVal> likeValList;

    @Builder
    public Posts(String title, String content, String imageUrl, User user, Categories categories, int likeCount){
        this.title=title;
        this.content=content;
        this.imageUrl=imageUrl;
        this.user=user;
        this.categories=categories;
        this.likeCount=likeCount;
    }

    public void update(String title, String content){
        this.title=title;
        this.content=content;
    }

    public void plusCount(){
        this.likeCount+=1;
    }

    public void minusCount(){
        this.likeCount-=1;
    }

}
