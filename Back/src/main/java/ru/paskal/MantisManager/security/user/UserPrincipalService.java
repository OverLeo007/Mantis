package ru.paskal.MantisManager.security.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.paskal.MantisManager.entities.BoardList;
import ru.paskal.MantisManager.entities.Task;
import ru.paskal.MantisManager.entities.User;
import ru.paskal.MantisManager.exceptions.AccessForbiddenException;
import ru.paskal.MantisManager.exceptions.notFound.UserNotFoundException;
import ru.paskal.MantisManager.services.BoardListService;
import ru.paskal.MantisManager.services.TaskService;
import ru.paskal.MantisManager.services.UserService;

@Service
@RequiredArgsConstructor
public class UserPrincipalService {

  private final UserService userService;
  private final BoardListService boardListService;
  private final TaskService taskService;

  public User getUserFromPrincipal(UserPrincipal principal) throws UserNotFoundException {
    if (principal == null) {
      throw new AccessForbiddenException("Unauthorized");
    }
    return userService.getOne(principal.getId());
  }

  @Transactional(readOnly = true)
  public void hasAccessToBoard(UserPrincipal principal, Integer boardId)
      throws AccessForbiddenException {
    User user = getUserFromPrincipal(principal);
    if (user.getBoards().stream().noneMatch(board -> board.getId().equals(boardId))) {
      if (!user.getSimpleRole().equals("ROLE_ADMIN")) {
        throw new AccessForbiddenException(
            user.getUsername() + " has not access for board with id = " + boardId
        );
      }
    }
  }

  @Transactional(readOnly = true)
  public BoardList getAccessToBoardList(UserPrincipal principal, Integer listId)
      throws AccessForbiddenException {
    var list = boardListService.getById(listId);
    hasAccessToBoard(principal, list.getBoard().getId());
    return list;
  }

  @Transactional(readOnly = true)
  public Task getAccessToTask(UserPrincipal principal, Integer taskId)
      throws AccessForbiddenException {
    var task = taskService.getById(taskId);
    getAccessToBoardList(principal, task.getList().getId());
    return task;
  }

}
