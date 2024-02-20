package ru.paskal.MantisManager.services;


import static ru.paskal.MantisManager.utils.TestLogger.log;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.paskal.MantisManager.dao.TaskDao;
import ru.paskal.MantisManager.dto.task.TaskCreateDto;
import ru.paskal.MantisManager.dto.task.TaskDtoToSend;
import ru.paskal.MantisManager.dto.task.TaskToEditDto;
import ru.paskal.MantisManager.exceptions.notFound.BoardListNotFoundException;
import ru.paskal.MantisManager.exceptions.notFound.BoardNotFoundException;
import ru.paskal.MantisManager.exceptions.notFound.TaskNotFoundException;
import ru.paskal.MantisManager.models.Task;
import ru.paskal.MantisManager.repositories.BoardListRepository;
import ru.paskal.MantisManager.repositories.TaskRepository;

@Service
@Transactional(readOnly = true)
public class TaskService {
  private final TaskRepository repository;

  private final BoardListRepository boardListRepository;
  private final TaskDao taskDao;

  private final ModelMapper mm;


  @Autowired
  public TaskService(TaskRepository repository, BoardListRepository boardListRepository,
      TaskDao taskDao, ModelMapper mm) {
    this.repository = repository;
    this.boardListRepository = boardListRepository;
    this.taskDao = taskDao;
    this.mm = mm;
  }


  public TaskDtoToSend getTaskById(Integer id) {
    return taskDao.mapTask(repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id)));
  }

  @Transactional
  public TaskDtoToSend saveTask(TaskCreateDto task) {
    var newTask = repository.save(mapFromCreateDto(task));
    log("savedTask  Task: " + repository.save(newTask));
    return taskDao.mapTask(newTask);

//    repository.save(task);
  }

  @Transactional
  public void saveTask(TaskToEditDto task) {
    var localTask = repository.findById(task.getId()).orElseThrow(() -> new TaskNotFoundException(task.getId()));
    localTask.setTaskPreferences(task.getTaskPreferences().toString());
    localTask.setTaskPosition(task.getTaskPosition());
    localTask.setDueDate(task.getDueDate());
    localTask.setTaskText(task.getTaskText());
    localTask.setTaskTitle(task.getTaskTitle());
    log(localTask);
    log("Updated  Task: " + repository.save(localTask));
  }

  private Task mapFromCreateDto(TaskCreateDto taskCreateDto) {
    var task = new Task();
    task.setTaskTitle(taskCreateDto.getTaskTitle());
    task.setTaskPosition(taskCreateDto.getTaskPosition());
    task.setTaskPreferences("{\"color\": \"#ffffff\"}");
    Integer listId = taskCreateDto.getListId();
    task.setList(
        boardListRepository.findById(listId).orElseThrow(
            () -> new BoardListNotFoundException(listId)
        )
    );

    return task;
  }

  @Transactional
  public void delete(Integer id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
    } else {
      throw new TaskNotFoundException(id);
    }
  }

}
