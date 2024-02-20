package ru.paskal.MantisManager.dto;

import java.sql.Timestamp;
import lombok.Data;
import ru.paskal.MantisManager.dto.user.UserDtoForLink;

@Data
public class CommentDto {
  private Integer id;
  private UserDtoForLink user;
  private String text;
  private Timestamp commentDate;
}
