package ru.paskal.MantisManager.controllers;


import static ru.paskal.MantisManager.utils.TestLogger.log;
import static ru.paskal.MantisManager.utils.staticUtils.okResponseWrapper;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import ru.paskal.MantisManager.dto.task.TaskCreateDto;
import ru.paskal.MantisManager.dto.task.TaskDtoToSend;
import ru.paskal.MantisManager.dto.task.TaskToEditDto;
import ru.paskal.MantisManager.exceptions.notCreated.TaskNotCreatedException;
import ru.paskal.MantisManager.exceptions.notDeleted.TaskNotDeletedException;
import ru.paskal.MantisManager.exceptions.notFound.TaskNotFoundException;
import ru.paskal.MantisManager.exceptions.notUpdated.TaskNotUpdatedException;
import ru.paskal.MantisManager.models.Task;
import ru.paskal.MantisManager.services.TaskService;
import ru.paskal.MantisManager.utils.CrudErrorHandlers;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/tasks")
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


  @Autowired
  public TaskController(TaskService taskService, TaskDao taskDao, ModelMapper mm) {
    this.taskService = taskService;
    this.taskDao = taskDao;
    this.mm = mm;
  }

  @GetMapping
  public ResponseEntity<List<TaskDtoToSend>> getByList(@RequestParam(name = "list_id") Integer listId) {
    var task = taskDao.getTasksByListId(listId);
    log(task);
    return okResponseWrapper(task);
  }

  @GetMapping("{id}")
  public ResponseEntity<TaskDtoToSend> getById(@PathVariable(name = "id") Integer taskId) {
    var task = taskService.getTaskById(taskId);
    log(task);
    return okResponseWrapper(task);
  }

  @PostMapping
  public ResponseEntity<TaskDtoToSend> createTask(@RequestBody TaskCreateDto taskCreateDto) {
    try {
      var task = taskService.saveTask(taskCreateDto);
      log(task);
      return okResponseWrapper(task);
    } catch (Exception e) {
      throw new TaskNotCreatedException(e.getMessage());
    }
  }

  @PutMapping
  public ResponseEntity<HttpStatus> editTask(@RequestBody TaskToEditDto taskToEditDto) {
    try {
      log(taskToEditDto);
      taskService.saveTask(taskToEditDto);
    } catch (Exception e) {
      throw new TaskNotUpdatedException(e.getMessage());
    }
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteTask(@PathVariable Integer id) {
    try {
      taskService.delete(id);
    } catch (TaskNotFoundException e) {
      throw new TaskNotDeletedException(e.getMessage());
    }
    return ResponseEntity.ok().build();
  }


}
