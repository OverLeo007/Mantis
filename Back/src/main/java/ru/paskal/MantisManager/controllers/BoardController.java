package ru.paskal.MantisManager.controllers;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
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
import ru.paskal.MantisManager.dao.BoardDao;
import ru.paskal.MantisManager.models.dto.board.BoardDto;
import ru.paskal.MantisManager.models.dto.board.BoardDtoForLink;
import ru.paskal.MantisManager.models.dto.BoardListDto;
import ru.paskal.MantisManager.models.dto.task.TaskDtoToSend;
import ru.paskal.MantisManager.models.dto.user.UserDtoForLink;
import ru.paskal.MantisManager.exceptions.notCreated.BoardNotCreatedException;
import ru.paskal.MantisManager.exceptions.notDeleted.BoardNotDeletedException;
import ru.paskal.MantisManager.exceptions.notFound.BoardNotFoundException;
import ru.paskal.MantisManager.exceptions.notFound.UserNotFoundException;
import ru.paskal.MantisManager.exceptions.notUpdated.BoardNotUpdatedException;
import ru.paskal.MantisManager.entities.Board;
import ru.paskal.MantisManager.services.BoardService;
import ru.paskal.MantisManager.utils.CrudErrorHandlers;

//import static ru.paskal.MantisManager.utils.TestLogger.log;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/boards")
@RequiredArgsConstructor
@Slf4j
public class BoardController extends
    CrudErrorHandlers<
        BoardNotCreatedException,
        BoardNotFoundException,
        BoardNotUpdatedException,
        BoardNotDeletedException
        > {

  private final BoardService boardService;
  private final BoardDao boardDao;
  private final ModelMapper modelMapper;


  @GetMapping
  public List<BoardDtoForLink> getBoards(
      @RequestParam(name = "user", required = false) Integer uid) {
    if (uid != null) {
      try {
        log.info("Got boards by userid: " + uid);
        return boardService.getByUser(uid).stream()
            .map(board -> modelMapper.map(board, BoardDtoForLink.class)).toList();
      } catch (UserNotFoundException e) {
        throw new BoardNotFoundException(e.getMessage());
      }
    }
    return boardService.getAll().stream()
        .map(board -> modelMapper.map(board, BoardDtoForLink.class)).toList();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public void create(@RequestBody JsonNode json) {
    try {
      log.info("Trying to create board: " + json.toString());
      // TODO: Сделать возвращение результата
      boardService.save(json.get("title").asText(), json.get("user_id").asInt());
    } catch (UserNotFoundException e) {
      log.info("Failed creating board: " + json + ", with " + e.getMessage());
      throw new BoardNotCreatedException(e.getMessage());
    }
    log.info("Success created board: " + json);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void edit(@PathVariable Integer id, @RequestBody JsonNode json) {
    try {
      log.info("Trying to edit board id=" + id + ": " + json.toString());
      boardService.update(id, json.get("title"));
    } catch (BoardNotFoundException e) {
      log.info("Failed editing board id=" + id + ": " + json);
      throw new BoardNotUpdatedException(e.getMessage());
    }
    log.info("Success editing board id=" + id + ": " + json);

  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void delete(@PathVariable Integer id) {
    try {
      log.info("Trying to delete board id=" + id);
      boardService.delete(id);
    } catch (BoardNotFoundException e) {
      log.info("Failed deleting board: " + id);
      throw new BoardNotDeletedException(e.getMessage());
    }
    log.info("Success deleting board id=" + id);
  }


  private BoardDto convertBoardToDto(Board board) {
    BoardDto boardDto = new BoardDto();
    if (board.getLists() != null) {
      boardDto.setLists(
          board.getLists().stream().map(
              list -> {
                BoardListDto boardListDto = new BoardListDto();
                boardListDto.setTasks(
                    list.getTasks().stream().map(
                        task -> modelMapper.map(task, TaskDtoToSend.class)
                    ).toList()
                );
                modelMapper.map(list, boardListDto);
                return boardListDto;
              }
          ).toList()
      );
    }
    boardDto.setUsers(
        board.getUsers().stream().map(user -> modelMapper.map(user, UserDtoForLink.class))
            .toList());
    boardDto.setId(board.getId());
    boardDto.setTitle(board.getTitle());
    return boardDto;
  }
}
