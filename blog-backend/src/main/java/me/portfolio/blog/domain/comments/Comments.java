package me.portfolio.blog.domain.comments;

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
public class Comments extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comments_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posts_id", nullable = false)
    private Posts posts;

    @Column(nullable = false)
    private String body;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="parents_id", referencedColumnName = "comments_id", columnDefinition = "bigint default 0")
    private Comments parents;

    @JsonIgnore
    @OneToMany(mappedBy = "parents", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Comments> replies;

    @Builder
    public Comments(Posts posts, String body, Comments parents, User user){
        this.posts = posts;
        this.body = body;
        this.parents=parents;
        this.user = user;
    }

    public void update(String body){
        this.body=body;
    }

}
