package ru.paskal.MantisManager.models.dto.task;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Date;
import lombok.Data;

@Data
public class TaskToEditDto {
  private Integer id;
  private String taskText;
  private Integer taskPosition;
  private String taskTitle;
  private Date dueDate;
  private JsonNode taskPreferences;
}
