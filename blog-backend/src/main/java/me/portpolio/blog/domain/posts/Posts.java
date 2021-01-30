package me.portpolio.blog.domain.posts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.portpolio.blog.domain.BaseTimeEntity;
import me.portpolio.blog.domain.comments.Comments;

import javax.persistence.*;
import java.util.ArrayList;
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

    @Column(nullable = false)
    private String author;

    @Column(columnDefinition = "varchar(255) default './images/default.jpg'")
    private String imageUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "posts", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comments> commentsList = new ArrayList<>();

    @Builder
    public Posts(String title, String content, String author, String imageUrl){
        this.title=title;
        this.content=content;
        this.author=author;
        this.imageUrl=imageUrl;
    }

    public void update(String title, String content){
        this.title=title;
        this.content=content;
    }

}
