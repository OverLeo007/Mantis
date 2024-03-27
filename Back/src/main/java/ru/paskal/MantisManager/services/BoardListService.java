package ru.paskal.MantisManager.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.paskal.MantisManager.dao.BoardDao;
import ru.paskal.MantisManager.exceptions.JsonParsingException;
import ru.paskal.MantisManager.exceptions.notFound.BoardListNotFoundException;
import ru.paskal.MantisManager.exceptions.notFound.BoardNotFoundException;
import ru.paskal.MantisManager.entities.Board;
import ru.paskal.MantisManager.entities.BoardList;
import ru.paskal.MantisManager.repositories.BoardListRepository;
import ru.paskal.MantisManager.repositories.BoardRepository;

// TODO: rolling file для логов создать

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardListService {
  private final BoardListRepository repository;

  private final BoardRepository boardRepository;
  private final BoardDao boardDao;

  public List<BoardList> getByBoardId(Integer boardId) {
    return repository.findByBoardId(boardId);
  }

  public BoardList getById(Integer id) {
    return repository.findById(id).orElseThrow(() -> new BoardListNotFoundException(id));
  }

  @Transactional
  public void update(int id, BoardList boardList) {
    BoardList existingList = repository.findById(id).orElseThrow(() -> new BoardListNotFoundException(id));

    existingList.setTitle(boardList.getTitle());
    existingList.setListPosition(boardList.getListPosition());

    repository.save(existingList);
  }

  @Transactional
  public void create(JsonNode json)
      throws JsonProcessingException, JsonParsingException, BoardNotFoundException {
    JsonNode boardId = json.get("boardId");
    JsonNode title = json.get("title");
    if (boardId == null || title == null) {
      throw new JsonParsingException(json);
    }

    Board board = boardRepository.findById(boardId.asInt()).orElseThrow(() -> new BoardNotFoundException(boardId.asInt()));
    BoardList newList =  new BoardList();
    newList.setTitle(title.asText());
    newList.setBoard(board);
    newList.setListPosition(boardDao.getNewPosition(boardId.asInt()));


    if (board.getLists() != null) {
      board.getLists().add(newList);
    } else {
      board.setLists(Collections.singletonList(newList));
    }
    boardRepository.save(board);
    repository.save(newList);
  }

  @Transactional
  public void delete(Integer id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
    } else {
      throw new BoardListNotFoundException(id);
    }
  }

//  public BoardList getBoardList
}
