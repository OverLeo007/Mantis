package ru.paskal.MantisManager.services;



import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.paskal.MantisManager.dao.TaskDao;
import ru.paskal.MantisManager.models.dto.task.TaskCreateDto;
import ru.paskal.MantisManager.models.dto.task.TaskDtoToSend;
import ru.paskal.MantisManager.models.dto.task.TaskToEditDto;
import ru.paskal.MantisManager.exceptions.notFound.BoardListNotFoundException;
import ru.paskal.MantisManager.exceptions.notFound.TaskNotFoundException;
import ru.paskal.MantisManager.entities.Task;
import ru.paskal.MantisManager.repositories.BoardListRepository;
import ru.paskal.MantisManager.repositories.TaskRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TaskService {
  private final TaskRepository repository;

  private final BoardListRepository boardListRepository;
  private final TaskDao taskDao;

  private final ModelMapper mm;

  public TaskDtoToSend getTaskDtoById(Integer id) {
    return taskDao.mapTask(repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id)));
  }

  public Task getById(Integer id) {
    return repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
  }

  public List<TaskDtoToSend> getByListId(Integer id) {
    return taskDao.getTasksByListId(id);
  }




  @Transactional
  public TaskDtoToSend saveTask(TaskCreateDto task) {
    var newTask = repository.save(mapFromCreateDto(task));
    log.info("savedTask  Task: " + repository.save(newTask));
    return taskDao.mapTask(newTask);

//    repository.save(task);
  }


  @Transactional
  public void saveTask(TaskToEditDto task) {
    var localTask = repository.findById(task.getId())
        .orElseThrow(() -> new TaskNotFoundException(task.getId()));
    localTask.setTaskPreferences(task.getTaskPreferences().toString());
    localTask.setTaskPosition(task.getTaskPosition());
    localTask.setDueDate(task.getDueDate());
    localTask.setTaskText(task.getTaskText());
    localTask.setTaskTitle(task.getTaskTitle());
    log.info(localTask.toString());
    log.info("Updated  Task: " + repository.save(localTask));
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
