package ru.paskal.MantisManager.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import ru.paskal.MantisManager.dao.BoardListDao;
import ru.paskal.MantisManager.dao.TaskDao;
import ru.paskal.MantisManager.dto.BoardListDto;
import ru.paskal.MantisManager.exceptions.JsonParsingException;
import ru.paskal.MantisManager.exceptions.notCreated.BoardListNotCreatedException;
import ru.paskal.MantisManager.exceptions.notDeleted.BoardListNotDeletedException;
import ru.paskal.MantisManager.exceptions.notFound.BoardListNotFoundException;
import ru.paskal.MantisManager.exceptions.notFound.BoardNotFoundException;
import ru.paskal.MantisManager.exceptions.notUpdated.BoardListNotUpdatedException;
import ru.paskal.MantisManager.models.BoardList;
import ru.paskal.MantisManager.services.BoardListService;
import ru.paskal.MantisManager.utils.CrudErrorHandlers;

//TODO: Метод свапа листов
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/lists")
@RequiredArgsConstructor
public class BoardListController extends
    CrudErrorHandlers<
        BoardListNotCreatedException,
        BoardListNotFoundException,
        BoardListNotUpdatedException,
        BoardListNotDeletedException
        > {

  private final BoardListService boardListService;
  private final BoardListDao boardListDao;

  private final TaskDao taskDao;
  private final ModelMapper modelMapper;

  private final Logger log;

  @GetMapping("/{id}")
  public BoardListDto getList(@PathVariable int id) {
    log.info("Got lists by list id: " + id);
    return convertListToDto(boardListService.getById(id));
  }

  @GetMapping
  public List<BoardListDto> getLists(
      @RequestParam(name = "board", required = false) Integer boardId) {
    log.info("Got lists by board id: " + boardId);
    if (boardId != null) {
      try {
        return boardListDao.getLists(boardId).stream().map(this::convertListToDto).toList();
      } catch (BoardNotFoundException e) {
        throw new BoardListNotFoundException(e.getMessage());
      }
    } else {
      return boardListDao.getLists().stream().map(this::convertListToDto).toList();
    }
  }

  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public void editList(@PathVariable Integer id, @RequestBody BoardList boardList) {
    try {
      log.info("Trying to edit list id=" + id + ": ");
      boardListService.update(id, boardList);
    } catch (BoardListNotFoundException e) {
      log.info("Failed editing list id=" + id + ": " + boardList.toString());
      throw new BoardListNotUpdatedException(e.getMessage());
    }
    log.info("Success editing list id=" + id + ": " + boardList.toString());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public void createList(@RequestBody JsonNode json) {
    try {
      log.info("Trying to create list: " + json.toString());

      // TODO: Сделать возвращение результата
      boardListService.create(json);
    } catch (JsonProcessingException | JsonParsingException | BoardNotFoundException e) {
      log.info("Failed creating list: " + json + ", with " + e.getMessage());
      throw new BoardListNotCreatedException(e.getMessage());
    }
    log.info("Success created list: " + json);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteList(@PathVariable Integer id) {
    try {
      log.info("Trying to delete list id=" + id);

      boardListService.delete(id);
    } catch (BoardListNotFoundException e) {
      log.info("Failed deleting list: " + id);
      throw new BoardListNotDeletedException(e.getMessage());
    }
    log.info("Success deleting list id=" + id);
  }


  private BoardListDto convertListToDto(BoardList list) {
    BoardListDto boardListDto = new BoardListDto();
    boardListDto.setTasks(taskDao.getTasksByListId(list.getId()));
    boardListDto.setListPosition(list.getListPosition());
    boardListDto.setTitle(list.getTitle());
    boardListDto.setId(list.getId());
//    modelMapper.map(list, boardListDto);
    return boardListDto;

  }
}
