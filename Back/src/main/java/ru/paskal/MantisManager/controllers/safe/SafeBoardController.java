package ru.paskal.MantisManager.controllers.safe;

import java.util.InputMismatchException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.paskal.MantisManager.entities.User;
import ru.paskal.MantisManager.exceptions.AccessForbiddenException;
import ru.paskal.MantisManager.exceptions.notCreated.BoardNotCreatedException;
import ru.paskal.MantisManager.exceptions.notDeleted.BoardNotDeletedException;
import ru.paskal.MantisManager.exceptions.notFound.BoardNotFoundException;
import ru.paskal.MantisManager.exceptions.notFound.UserNotFoundException;
import ru.paskal.MantisManager.exceptions.notUpdated.BoardNotUpdatedException;
import ru.paskal.MantisManager.models.dto.board.BoardDtoForLink;
import ru.paskal.MantisManager.models.dto.board.BoardTitleRequest;
import ru.paskal.MantisManager.security.user.UserPrincipal;
import ru.paskal.MantisManager.security.user.UserPrincipalService;
import ru.paskal.MantisManager.services.BoardService;
import ru.paskal.MantisManager.utils.EntityMapper;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/s/boards")
@RequiredArgsConstructor
@Slf4j
public class SafeBoardController {

  private final BoardService boardService;
  private final UserPrincipalService userPrincipalService;
//  private final ModelMapper modelMapper;
  private final EntityMapper em;


  // TODO: Глобал админ не может иметь доступ ко всей информации (пересмотреть доступ для админа)
  @GetMapping
  public List<BoardDtoForLink> getBoards(
      @AuthenticationPrincipal UserPrincipal principal
  ) {
    try {
      User user = userPrincipalService.getUserFromPrincipal(principal);
      if (user.getSimpleRole().equals("ROLE_USER")) {
        log.info("Got boards by userid: " + user.getId());
        return em.mapToBoardLinks(boardService.getByUser(user.getId()));
      } else if (user.getSimpleRole().equals("ROLE_ADMIN")) {
        return em.mapToBoardLinks(boardService.getAll());
      }
    } catch (UserNotFoundException e) {
      throw new BoardNotFoundException(e.getMessage());
    }
    throw new InputMismatchException("Something with roles has broken: " + principal);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public void create(
      @RequestBody BoardTitleRequest request,
      @AuthenticationPrincipal UserPrincipal principal
  ) {
    try {
      User user = userPrincipalService.getUserFromPrincipal(principal);
      log.info(user.getUsername() +  " trying to create board: " + request.getTitle());
      boardService.save(request.getTitle(), user);
    } catch (UserNotFoundException e) {
      log.info("Failed creating board: " + request + ", with " + e.getMessage());
      throw new BoardNotCreatedException(e.getMessage());
    }
    log.info("Success created board: " + request);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void edit(
      @PathVariable Integer id,
      @RequestBody BoardTitleRequest request,
      @AuthenticationPrincipal UserPrincipal principal
  ) {
    try {
      userPrincipalService.hasAccessToBoard(principal, id);
      log.info("Trying to edit board id=" + id + ": " + request.getTitle());
      boardService.update(id, request.getTitle());
    } catch (UserNotFoundException | AccessForbiddenException e ) {
      log.info("Failed editing board id=" + id + ": " + request.getTitle());
      throw new BoardNotUpdatedException(e.getMessage());
    }
    log.info("Success editing board id=" + id + ": " + request.getTitle());
  }


  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void delete(
      @PathVariable Integer id,
      @AuthenticationPrincipal UserPrincipal principal
  ) {
    try {
      userPrincipalService.hasAccessToBoard(principal, id);
      log.info("Trying to delete board id=" + id);
      boardService.delete(id);
    } catch (AccessForbiddenException e) {
      log.info("Failed deleting board id= " + id);
      throw new BoardNotDeletedException(e.getMessage());
    }
    log.info("Success deleting board id=" + id);
  }





}
