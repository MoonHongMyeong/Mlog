package me.portpolio.blog.domain.comments;

import me.portpolio.blog.web.dto.comments.CommentsResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CommentsRepository extends JpaRepository<Comments, Long> {
    @Query("select c from Comments c join fetch c.parents")
    List<CommentsResponseDto> findByParents(Comments parents);
}
