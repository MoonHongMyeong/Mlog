package me.portfolio.blog.domain.like;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portfolio.blog.domain.BaseTimeEntity;
import me.portfolio.blog.domain.posts.Posts;
import me.portfolio.blog.domain.user.User;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class LikeVal extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LikeVal_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "posts_id")
    private Posts posts;

    @Builder
    public LikeVal(User user, Posts posts){
        this.user=user;
        this.posts=posts;
    }
}
