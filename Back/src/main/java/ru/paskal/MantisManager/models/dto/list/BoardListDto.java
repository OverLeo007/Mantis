package ru.paskal.MantisManager.models.dto.list;

import java.util.List;
import lombok.Data;
import ru.paskal.MantisManager.models.dto.task.TaskDtoToSend;

@Data
public class BoardListDto {
  private Integer id;
  private String title;
  private Integer listPosition;
  private List<TaskDtoToSend> tasks;

}
