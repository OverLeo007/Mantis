package ru.paskal.MantisManager.dto.task;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Date;
import java.util.List;
import lombok.Data;
import ru.paskal.MantisManager.dto.CommentDto;
import ru.paskal.MantisManager.dto.LabelDtoForTask;
import ru.paskal.MantisManager.dto.user.UserDtoForLink;


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
