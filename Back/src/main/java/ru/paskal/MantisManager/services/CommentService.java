package ru.paskal.MantisManager.services;

import java.sql.Timestamp;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.paskal.MantisManager.entities.Comment;
import ru.paskal.MantisManager.entities.User;
import ru.paskal.MantisManager.exceptions.notFound.CommentNotFoundException;
import ru.paskal.MantisManager.exceptions.notFound.TaskNotFoundException;
import ru.paskal.MantisManager.models.dto.comments.CommentDto;
import ru.paskal.MantisManager.models.dto.comments.CreateCommentRequest;
import ru.paskal.MantisManager.models.dto.comments.EditCommentRequest;
import ru.paskal.MantisManager.repositories.CommentRepository;
import ru.paskal.MantisManager.repositories.TaskRepository;
import ru.paskal.MantisManager.utils.EntityMapper;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository repository;
  private final TaskRepository taskRepository;

  private final EntityMapper em;


  @Transactional
  public Comment create(CreateCommentRequest request, User user) {
    var newComment = new Comment();
    var task = taskRepository.findById(request.getTaskId())
        .orElseThrow(() -> new TaskNotFoundException(request.getTaskId()));
    newComment.setTask(task);
    newComment.setUser(user);
    newComment.setText(request.getText());
    newComment.setCommentDate(new Timestamp(new java.util.Date().getTime()));
    if (request.getParentId() != null) {
      newComment.setParentComment(repository.findById(request.getParentId()).orElseThrow(
          () -> new CommentNotFoundException(request.getParentId())
      ));
    }
    return repository.save(newComment);
  }

  public Comment getById(Integer commentId) {
    return repository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
  }

  @Transactional
  public Comment update(Integer id, EditCommentRequest request) {
    var comment = getById(id);
    comment.setText(request.getNewText());
    comment.setCommentDate(new Timestamp(new java.util.Date().getTime()));
    return repository.save(comment);
  }

  @Transactional
  public void delete(Integer id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
    } else {
      throw new CommentNotFoundException(id);
    }
  }

  public List<Comment> getByTask(Integer taskId) {
    return repository.getCommentsByTaskId(taskId);
  }

  public CommentDto convertCommentToDto(Comment comment) {
    var dto = new CommentDto();
    dto.setId(comment.getId());
    dto.setUser(em.mapToUserLink(comment.getUser()));
    dto.setText(comment.getText());
    dto.setCommentDate(comment.getCommentDate());
    var parent = comment.getParentComment();
    if (parent != null) {
      dto.setParentId(parent.getId());
    }
    return dto;
  }
}
