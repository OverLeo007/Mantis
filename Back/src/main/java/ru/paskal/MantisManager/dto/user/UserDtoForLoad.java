package ru.paskal.MantisManager.dto.user;

import java.util.List;
import lombok.Data;
import ru.paskal.MantisManager.dto.board.BoardDtoForLink;

@Data
public class UserDtoForLoad {
  private  int id;
  private  String username;
  private List<BoardDtoForLink> boards;
}
