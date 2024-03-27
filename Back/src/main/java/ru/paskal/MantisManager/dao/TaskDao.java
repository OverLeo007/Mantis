package ru.paskal.MantisManager.dao;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.paskal.MantisManager.models.dto.CommentDto;
import ru.paskal.MantisManager.models.dto.LabelDtoForTask;
import ru.paskal.MantisManager.models.dto.task.TaskDtoToSend;
import ru.paskal.MantisManager.models.dto.user.UserDtoForLink;
import ru.paskal.MantisManager.entities.Task;
import ru.paskal.MantisManager.entities.User;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskDao extends Dao {

  public static final String tasksWithLabels =
      "SELECT t from Task t LEFT JOIN FETCH t.labels l WHERE t.list.id = :listId";


  private final ModelMapper mm;

  public List<TaskDtoToSend> getTasksByListId(Integer listId) {
    var session = getSession();
    var tasks = session.createQuery(
        tasksWithLabels,
        Task.class
    ).setParameter("listId", listId).getResultList();
//    log(tasks.get(0).getTaskDoer(), this);
    return mapTasks(tasks);
  }


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
              .stream().map(comment -> mm.map(comment, CommentDto.class)).toList()
      );
    }
    mm.map(task, taskDtoToSend);
    return taskDtoToSend;
  }

  public List<TaskDtoToSend> mapTasks(List<Task> tasks) {
    return tasks.stream().map(this::mapTask).toList();
  }
}
