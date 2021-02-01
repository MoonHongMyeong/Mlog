package me.portpolio.blog.domain.comments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portpolio.blog.domain.BaseTimeEntity;
import me.portpolio.blog.domain.posts.Posts;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parents_id", referencedColumnName = "comments_id", columnDefinition = "bigint default 0")
    private Comments parents;

    @JsonIgnore
    @OneToMany(mappedBy = "parents", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Comments> replies;

    @Builder
    public Comments(Posts posts, String body, String author, Comments parents){
        this.posts = posts;
        this.body = body;
        this.author = author;
        this.parents=parents;
    }

    public void update(String body){
        this.body=body;
    }

}
