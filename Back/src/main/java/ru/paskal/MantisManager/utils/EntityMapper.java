package ru.paskal.MantisManager.utils;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.paskal.MantisManager.entities.Board;
import ru.paskal.MantisManager.entities.User;
import ru.paskal.MantisManager.models.dto.board.BoardDtoForLink;
import ru.paskal.MantisManager.models.dto.user.UserDtoForLink;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EntityMapper {

  private final ModelMapper mm;


  public List<BoardDtoForLink> mapToBoardLinks(List<Board> boards) {
    return boards.stream()
        .map(board -> mm.map(board, BoardDtoForLink.class)).toList();
  }

  public UserDtoForLink mapToUserLink(User user) {
    return mm.map(user, UserDtoForLink.class);
  }

}
