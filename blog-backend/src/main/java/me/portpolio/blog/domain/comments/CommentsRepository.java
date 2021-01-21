package me.portpolio.blog.domain.comments;

import me.portpolio.blog.domain.posts.Posts;
import me.portpolio.blog.web.dto.comments.CommentsResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
}
