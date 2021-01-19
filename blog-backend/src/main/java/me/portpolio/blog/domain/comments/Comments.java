package me.portpolio.blog.domain.comments;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portpolio.blog.domain.BaseTimeEntity;
import me.portpolio.blog.domain.posts.Posts;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comments extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comments_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "posts_id")
    private Posts posts;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private String author;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "comments_class", columnDefinition = "bigint default 0")
    private Long coClass;

    @Column(name = "comments_order")
    private Long coOrder;

    @Builder
    public Comments(Posts posts, String body, String author){
        this.posts = posts;
        this.body = body;
        this.author = author;
    }

    public void update(String body, String author){
        this.body=body;
        this.author=author;
    }

}
