package ru.paskal.MantisManager.security.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.paskal.MantisManager.entities.User;
import ru.paskal.MantisManager.exceptions.AccessForbiddenException;
import ru.paskal.MantisManager.exceptions.notFound.UserNotFoundException;
import ru.paskal.MantisManager.services.UserService;

@Service
@RequiredArgsConstructor
public class UserPrincipalService {

  private final UserService userService;

  public User getUserFromPrincipal(UserPrincipal principal) throws UserNotFoundException {
    return userService.getOne(principal.getId());
  }

  @Transactional(readOnly = true)
  public void hasAccessToBoard(UserPrincipal principal, Integer boardId) {
    User user = getUserFromPrincipal(principal);
    if (user.getBoards().stream().noneMatch(board -> board.getId().equals(boardId))) {
      throw new AccessForbiddenException(
          user.getUsername() + " has not access for board with id = " + boardId
      );
    }
  }
}
