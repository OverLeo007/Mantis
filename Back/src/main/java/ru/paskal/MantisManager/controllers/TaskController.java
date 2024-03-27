package ru.paskal.MantisManager.controllers;


import static ru.paskal.MantisManager.utils.staticUtils.okResponseWrapper;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import ru.paskal.MantisManager.models.dto.task.TaskCreateDto;
import ru.paskal.MantisManager.models.dto.task.TaskDtoToSend;
import ru.paskal.MantisManager.models.dto.task.TaskToEditDto;
import ru.paskal.MantisManager.exceptions.notCreated.TaskNotCreatedException;
import ru.paskal.MantisManager.exceptions.notDeleted.TaskNotDeletedException;
import ru.paskal.MantisManager.exceptions.notFound.TaskNotFoundException;
import ru.paskal.MantisManager.exceptions.notUpdated.TaskNotUpdatedException;
import ru.paskal.MantisManager.services.TaskService;
import ru.paskal.MantisManager.utils.CrudErrorHandlers;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController extends
    CrudErrorHandlers<
        TaskNotCreatedException,
        TaskNotFoundException,
        TaskNotUpdatedException,
        TaskNotDeletedException
        > {

  private final TaskService taskService;

  private final TaskDao taskDao;

  private final ModelMapper mm;


  @GetMapping
  public ResponseEntity<List<TaskDtoToSend>> getByList(@RequestParam(name = "list_id") Integer listId) {
    log.info("Trying to tasks by list id=" + listId);
    var task = taskDao.getTasksByListId(listId);
    log.info("Got task by list id=" + listId + ": " + task);
    return okResponseWrapper(task);
  }

  @GetMapping("{id}")
  public ResponseEntity<TaskDtoToSend> getById(@PathVariable(name = "id") Integer taskId) {
    log.info("Trying to tasks by id=" + taskId);
    var task = taskService.getTaskById(taskId);
    log.info("Got task by id=" + taskId + ": " + task);
    return okResponseWrapper(task);
  }

  @PostMapping
  public ResponseEntity<TaskDtoToSend> createTask(@RequestBody TaskCreateDto taskCreateDto) {
    try {
      log.info("Trying to create task: " + taskCreateDto);
      var task = taskService.saveTask(taskCreateDto);
      log.info("Success created task: " + task);
      return okResponseWrapper(task);
    } catch (Exception e) {
      log.info("Failed to create task: " + taskCreateDto);
      throw new TaskNotCreatedException(e.getMessage());
    }
  }

  @PutMapping
  public ResponseEntity<HttpStatus> editTask(@RequestBody TaskToEditDto taskToEditDto) {
    try {
      log.info("Trying to edit task: " + taskToEditDto);
      taskService.saveTask(taskToEditDto);
      log.info("Success edit task: " + taskToEditDto);
    } catch (Exception e) {
      log.info("Failed to edit task: " + taskToEditDto);
      throw new TaskNotUpdatedException(e.getMessage());
    }
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteTask(@PathVariable Integer id) {
    try {
      log.info("Trying to delete task id=" + id);
      taskService.delete(id);
      log.info("Success deleting task id=" + id);
    } catch (TaskNotFoundException e) {
      log.info("Failed deleting task id=" + id);
      throw new TaskNotDeletedException(e.getMessage());
    }
    return ResponseEntity.ok().build();
  }
}
