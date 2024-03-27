package ru.paskal.MantisManager.models.dto.task;

import lombok.Data;

@Data
public class TaskCreateDto {
  private String taskTitle;
  private Integer taskPosition;
  private Integer listId;
}
