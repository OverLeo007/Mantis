package ru.paskal.MantisManager.controllers.safe;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.paskal.MantisManager.entities.Comment;
import ru.paskal.MantisManager.exceptions.AccessForbiddenException;
import ru.paskal.MantisManager.exceptions.BadRequestException;
import ru.paskal.MantisManager.exceptions.notCreated.CommentNotCreatedException;
import ru.paskal.MantisManager.exceptions.notDeleted.CommentNotDeletedException;
import ru.paskal.MantisManager.exceptions.notFound.CommentNotFoundException;
import ru.paskal.MantisManager.exceptions.notFound.TaskNotFoundException;
import ru.paskal.MantisManager.exceptions.notUpdated.CommentNotUpdatedException;
import ru.paskal.MantisManager.models.dto.comments.CommentDto;
import ru.paskal.MantisManager.models.dto.comments.CreateCommentRequest;
import ru.paskal.MantisManager.models.dto.comments.EditCommentRequest;
import ru.paskal.MantisManager.security.user.UserPrincipal;
import ru.paskal.MantisManager.security.user.UserPrincipalService;
import ru.paskal.MantisManager.services.CommentService;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/s/comments")
@RequiredArgsConstructor
@Profile("with-security")
@Slf4j
public class SafeCommentController {
  private final CommentService commentService;
  private final UserPrincipalService userPrincipalService;

  @GetMapping
  public List<CommentDto> getByTask(
      @RequestParam(name = "task_id") Integer taskId,
      @AuthenticationPrincipal UserPrincipal principal
      ) {
    userPrincipalService.getAccessToTask(principal, taskId);
    return commentService.getByTask(taskId).stream().map(commentService::convertCommentToDto).toList();
  }


  @PostMapping
  private CommentDto createComment(
      @RequestBody CreateCommentRequest request,
      @AuthenticationPrincipal UserPrincipal principal
  ) {
    try {
      userPrincipalService.getAccessToTask(principal, request.getTaskId());
      return commentService.convertCommentToDto(commentService.create(request,
          userPrincipalService.getUserFromPrincipal(principal)));
    } catch (TaskNotFoundException e) {
      throw new CommentNotCreatedException(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public CommentDto editComment(
      @PathVariable Integer id,
      @RequestBody EditCommentRequest request,
      @AuthenticationPrincipal UserPrincipal principal
  ) {
    try {
      userPrincipalService.hasAccessToComment(principal, id);
      return commentService.convertCommentToDto(commentService.update(id, request));
    } catch (CommentNotFoundException | AccessForbiddenException e) {
      throw new CommentNotUpdatedException(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public void deleteComment(
      @PathVariable Integer id,
      @AuthenticationPrincipal UserPrincipal principal
  ) {
    try {
      userPrincipalService.hasAccessToComment(principal, id);
      commentService.delete(id);
    } catch (CommentNotFoundException e) {
      throw new CommentNotDeletedException(e.getMessage());
    }
  }
}
