package ru.paskal.MantisManager.controllers.safe;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import ru.paskal.MantisManager.dao.TaskDao;
import ru.paskal.MantisManager.exceptions.notCreated.TaskNotCreatedException;
import ru.paskal.MantisManager.exceptions.notDeleted.TaskNotDeletedException;
import ru.paskal.MantisManager.exceptions.notFound.TaskNotFoundException;
import ru.paskal.MantisManager.exceptions.notUpdated.TaskNotUpdatedException;
import ru.paskal.MantisManager.models.dto.task.TaskCreateDto;
import ru.paskal.MantisManager.models.dto.task.TaskDtoToSend;
import ru.paskal.MantisManager.models.dto.task.TaskToEditDto;
import ru.paskal.MantisManager.security.user.UserPrincipal;
import ru.paskal.MantisManager.security.user.UserPrincipalService;
import ru.paskal.MantisManager.services.TaskService;
import ru.paskal.MantisManager.utils.EntityMapper;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/s/tasks")
@RequiredArgsConstructor
@Profile("with-security")
@Slf4j
public class SafeTaskController {

  private final TaskService taskService;
  private final TaskDao taskDao;
  private final EntityMapper em;
  private final UserPrincipalService userPrincipalService;


  @GetMapping
  public List<TaskDtoToSend> getByList(
      @RequestParam(name = "list_id") Integer listId,
      @AuthenticationPrincipal UserPrincipal principal
  ) {
    userPrincipalService.getAccessToBoardList(principal, listId);
    return taskDao.getTasksByListId(listId);
  }

  @GetMapping("{id}")
  public TaskDtoToSend getById(
      @PathVariable(name = "id") Integer taskId,
      @AuthenticationPrincipal UserPrincipal principal
      ) {
    userPrincipalService.getAccessToTask(principal, taskId);
    return taskService.getTaskDtoById(taskId);
  }

  @PostMapping
  public TaskDtoToSend createTask(
      @RequestBody TaskCreateDto taskCreateDto,
      @AuthenticationPrincipal UserPrincipal principal
  ) {
    try {
      userPrincipalService.getAccessToBoardList(principal, taskCreateDto.getListId());
      return taskService.saveTask(taskCreateDto);
    } catch (Exception e) {
      throw new TaskNotCreatedException(e.getMessage());
    }
  }

  @PutMapping
  public ResponseEntity<HttpStatus> editTask(
      @RequestBody TaskToEditDto taskToEditDto,
      @AuthenticationPrincipal UserPrincipal principal
  ) {
    try {
      userPrincipalService.getAccessToTask(principal, taskToEditDto.getId());
      taskService.saveTask(taskToEditDto);
    } catch (Exception e) {
      throw new TaskNotUpdatedException(e.getMessage());
    }
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteTask(
      @PathVariable Integer id,
      @AuthenticationPrincipal UserPrincipal principal
  ) {
    try {
      userPrincipalService.getAccessToTask(principal, id);
      taskService.delete(id);
    } catch (TaskNotFoundException e) {
      throw new TaskNotDeletedException(e.getMessage());
    }
    return ResponseEntity.ok().build();
  }
}
