package me.portfolio.blog.domain.posts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portfolio.blog.domain.BaseTimeEntity;
import me.portfolio.blog.domain.comments.Comments;
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

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "varchar(255) default '/images/default.jpg'")
    private String imageUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "posts", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comments> commentsList;

    @Builder
    public Posts(String title, String content, String imageUrl, User user){
        this.title=title;
        this.content=content;
        this.imageUrl=imageUrl;
        this.user=user;
    }

    public void update(String title, String content){
        this.title=title;
        this.content=content;
    }

}
