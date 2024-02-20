package ru.paskal.MantisManager.dao;

import static ru.paskal.MantisManager.utils.TestLogger.log;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.paskal.MantisManager.dto.CommentDto;
import ru.paskal.MantisManager.dto.LabelDtoForTask;
import ru.paskal.MantisManager.dto.task.TaskDtoToSend;
import ru.paskal.MantisManager.dto.user.UserDtoForLink;
import ru.paskal.MantisManager.models.Task;
import ru.paskal.MantisManager.models.User;

@Component
@Transactional(readOnly = true)
public class TaskDao extends Dao {

  public static final String tasksWithLabels =
      "SELECT t from Task t LEFT JOIN FETCH t.labels l WHERE t.list.id = :listId";


  private final ModelMapper mm;

  @Autowired
  public TaskDao(ModelMapper mm) {
    this.mm = mm;
  }

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
