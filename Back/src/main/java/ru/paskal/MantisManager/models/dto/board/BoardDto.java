package ru.paskal.MantisManager.models.dto.board;

import java.util.List;
import lombok.Data;
import ru.paskal.MantisManager.models.dto.BoardListDto;
import ru.paskal.MantisManager.models.dto.user.UserDtoForLink;

@Data
public class BoardDto {
  private Integer id;
  private String title;
  private List<UserDtoForLink> users;
  private List<BoardListDto> lists;
}
