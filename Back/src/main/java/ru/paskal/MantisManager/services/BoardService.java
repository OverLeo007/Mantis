package ru.paskal.MantisManager.services;

import com.fasterxml.jackson.databind.JsonNode;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.paskal.MantisManager.exceptions.notFound.BoardNotFoundException;
import ru.paskal.MantisManager.exceptions.notFound.UserNotFoundException;
import ru.paskal.MantisManager.entities.Board;
import ru.paskal.MantisManager.repositories.BoardRepository;
import ru.paskal.MantisManager.repositories.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository repository;

  private final UserRepository userRepository;

  public Board getOne(int id) {

    return repository.findById(id).orElseThrow(() -> new BoardNotFoundException(id));
  }

  public List<Board> getAll() {
    return repository.findAll();
  }

  public List<Board> getByUser(int uid) {
    if (userRepository.existsById(uid)) {
      return repository.findByUsersId(uid);
    } else {
      throw new UserNotFoundException(uid);
    }
  }

  public List<Board> getByUserId(int uid) {
    return repository.findByUsersId(uid);
  }

  @Transactional
  public void save(String title, Integer creator_id) {
    Board board = new Board();
    board.setTitle(title);
    board.setLastEdit(new Timestamp(System.currentTimeMillis()));
    board.setUsers(Collections.singletonList(userRepository.findById(creator_id)
        .orElseThrow(() -> new UserNotFoundException(creator_id))));
    repository.save(board);
  }

  @Transactional
  public void update(int id, JsonNode title) {
    Board board = repository.findById(id).orElseThrow(() -> new BoardNotFoundException(id));
    if (!title.isNull()) {
      board.setTitle(title.asText());
    }
    repository.save(board);

  }

  @Transactional
  public void delete(Integer id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
    } else {
      throw new BoardNotFoundException(id);
    }
  }
}
