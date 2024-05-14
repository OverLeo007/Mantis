package ru.paskal.MantisManager.controllers.safe;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Profile;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.paskal.MantisManager.dao.TaskDao;
import ru.paskal.MantisManager.entities.BoardList;
import ru.paskal.MantisManager.exceptions.notCreated.BoardListNotCreatedException;
import ru.paskal.MantisManager.exceptions.notDeleted.BoardListNotDeletedException;
import ru.paskal.MantisManager.exceptions.notFound.BoardListNotFoundException;
import ru.paskal.MantisManager.exceptions.notFound.BoardNotFoundException;
import ru.paskal.MantisManager.exceptions.notUpdated.BoardListNotUpdatedException;
import ru.paskal.MantisManager.models.dto.list.BoardListCreateDto;
import ru.paskal.MantisManager.models.dto.list.BoardListDto;
import ru.paskal.MantisManager.security.user.UserPrincipal;
import ru.paskal.MantisManager.security.user.UserPrincipalService;
import ru.paskal.MantisManager.services.BoardListService;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/s/lists")
@RequiredArgsConstructor
@Profile("with-security")
@Slf4j
public class SafeBoardListController {

  private final UserPrincipalService userPrincipalService;
  private final ModelMapper modelMapper;
  private final BoardListService boardListService;
  private final TaskDao taskDao;


  @GetMapping("/{id}")
  public BoardListDto getList(
      @PathVariable int id,
      @AuthenticationPrincipal UserPrincipal principal
  ) {
    var list = userPrincipalService.getAccessToBoardList(principal, id);

    log.info("Got lists by list id: " + id);
    return convertListToDto(list);
  }

  @GetMapping
  public List<BoardListDto> getLists(
      @RequestParam(name = "board") Integer boardId,
      @AuthenticationPrincipal UserPrincipal principal
  ) {
    try {
      userPrincipalService.hasAccessToBoard(principal, boardId);
      return boardListService.getByBoardId(boardId).stream().map(this::convertListToDto).toList();

    } catch (BoardNotFoundException | BoardListNotFoundException e) {
      throw new BoardListNotFoundException(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void editList(
      @PathVariable Integer id,
      @RequestBody BoardList boardList,
      @AuthenticationPrincipal UserPrincipal principal
  ) {
    try {

      var list = userPrincipalService.getAccessToBoardList(principal, id);
      boardListService.update(id, boardList, list);
    } catch (BoardListNotFoundException e) {
      throw new BoardListNotUpdatedException(e.getMessage());
    }
  }

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public void createList(
      @RequestBody BoardListCreateDto request,
      @AuthenticationPrincipal UserPrincipal principal
  ) {
    try {
      userPrincipalService.hasAccessToBoard(principal, request.getBoardId());
      boardListService.create(request);
    } catch (BoardNotFoundException e) {
      throw new BoardListNotCreatedException(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteList(
      @PathVariable Integer id,
      @AuthenticationPrincipal UserPrincipal principal
  ) {
    try {
      var list = userPrincipalService.getAccessToBoardList(principal, id);
      boardListService.delete(list.getId());
    } catch (BoardListNotFoundException e) {
      throw new BoardListNotDeletedException(e.getMessage());
    }
  }


  private BoardListDto convertListToDto(BoardList list) {
    BoardListDto boardListDto = new BoardListDto();
    boardListDto.setTasks(taskDao.getTasksByListId(list.getId()));
    boardListDto.setListPosition(list.getListPosition());
    boardListDto.setTitle(list.getTitle());
    boardListDto.setId(list.getId());
    return boardListDto;
  }


}
