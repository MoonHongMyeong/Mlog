package me.portpolio.blog.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portpolio.blog.domain.BaseTimeEntity;
import me.portpolio.blog.domain.comments.Comments;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @Column(nullable = false)
    private String author;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)
    private Set<Comments> comments = new HashSet<>();

    @Builder
    public Posts(String title, String content, String author){
        this.title=title;
        this.content=content;
        this.author=author;
    }

    public void update(String title, String content){
        this.title=title;
        this.content=content;
    }
}
