package ru.paskal.MantisManager.models.dto.comments;

import lombok.Data;

@Data
public class CreateCommentRequest {
  private Integer taskId;
  private String text;
  private Integer parentId;
}
