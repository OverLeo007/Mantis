package ru.paskal.MantisManager.dao;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.paskal.MantisManager.models.dto.comments.CommentDto;
import ru.paskal.MantisManager.models.dto.LabelDtoForTask;
import ru.paskal.MantisManager.models.dto.task.TaskDtoToSend;
import ru.paskal.MantisManager.models.dto.user.UserDtoForLink;
import ru.paskal.MantisManager.entities.Task;
import ru.paskal.MantisManager.entities.User;

@Component
@RequiredArgsConstructor
public class TaskDao extends Dao {

  public static final String tasksWithLabels =
      "SELECT t from Task t LEFT JOIN FETCH t.labels l WHERE t.list.id = :listId";


  private final ModelMapper mm;

  @Transactional(readOnly = true)
  public List<TaskDtoToSend> getTasksByListId(Integer listId) {
    var session = getSession();
    var tasks = session.createQuery(
        tasksWithLabels,
        Task.class
    ).setParameter("listId", listId).getResultList();
//    log(tasks.get(0).getTaskDoer(), this);
    return mapTasks(tasks);
  }

  @Transactional(readOnly = true)
  public List<Task> getByListId(Integer listId) {
    var session = getSession();
    //    log(tasks.get(0).getTaskDoer(), this);
    return session.createQuery(
        tasksWithLabels,
        Task.class
    ).setParameter("listId", listId).getResultList();
  }

  @Transactional(readOnly = true)
  public TaskDtoToSend mapTask(Task task) {
    TaskDtoToSend taskDtoToSend = new TaskDtoToSend();
    User doer = task.getTaskDoer();
    var labels = task.getLabels();
    var comments = task.getComments();
    if (doer != null) {
      taskDtoToSend.setDoer(mm.map(doer, UserDtoForLink.class));
    }
    if (labels != null) {
      taskDtoToSend.setLabels(
          labels
              .stream().map(label -> mm.map(label, LabelDtoForTask.class)).toList());
    }
    if (comments != null) {
      taskDtoToSend.setComments(
          comments
              .stream().map(comment -> mm.map(comment, CommentDto.class)).toList() // TODO Сделать кастомную конвертацию
      );
    }
    mm.map(task, taskDtoToSend);
    return taskDtoToSend;
  }


  public List<TaskDtoToSend> mapTasks(List<Task> tasks) {
    return tasks.stream().map(this::mapTask).toList();
  }
}
