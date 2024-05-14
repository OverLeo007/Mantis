package ru.paskal.MantisManager.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.paskal.MantisManager.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
  List<Comment> getCommentsByTaskId(Integer taskId);

}
