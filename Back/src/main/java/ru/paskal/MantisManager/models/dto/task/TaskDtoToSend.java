package ru.paskal.MantisManager.models.dto.task;

import java.util.Date;
import java.util.List;
import lombok.Data;
import ru.paskal.MantisManager.models.dto.comments.CommentDto;
import ru.paskal.MantisManager.models.dto.LabelDtoForTask;
import ru.paskal.MantisManager.models.dto.user.UserDtoForLink;


@Data
public class TaskDtoToSend {
  private Integer id;
  private String taskTitle;
  private String taskText;
  private Integer taskPosition;
  private Date dueDate;
  private String taskPreferences;
  private List<LabelDtoForTask> labels;
  private UserDtoForLink doer;
  private List<CommentDto> comments;
}
//  @JsonIgnore
//  private UserDtoForLink taskDoer;
//  @JsonIgnore
//  private List<CommentDto> comments;
//  @JsonIgnore
//  private List<LabelDtoForTask> labels;
