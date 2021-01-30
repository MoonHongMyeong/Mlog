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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Posts posts;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private String author;

    @Column(name = "parent_id", columnDefinition = "bigint default 0")
    private Long parentId;

    @Column(name = "comments_class", columnDefinition = "bigint default 0")
    private Long coClass;

    @Column(name = "comments_order", columnDefinition = "bigint default 1")
    private Long coOrder;

    @Builder
    public Comments(Posts posts, String body, String author){
        this.posts = posts;
        this.body = body;
        this.author = author;
    }



    public void update(String body){
        this.body=body;
    }

}
