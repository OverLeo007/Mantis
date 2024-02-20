package ru.paskal.MantisManager.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.paskal.MantisManager.exceptions.notFound.BoardListNotFoundException;
import ru.paskal.MantisManager.exceptions.notFound.BoardNotFoundException;
import ru.paskal.MantisManager.models.BoardList;
import ru.paskal.MantisManager.models.Task;
import ru.paskal.MantisManager.repositories.BoardRepository;
import ru.paskal.MantisManager.utils.TestLogger;

@Component
@Transactional(readOnly = true)
public class BoardListDao extends Dao {
  public static final String listTasksById =
      "SELECT t from Task t WHERE t.list = :list";
  public static final String allLists =
      "SELECT l from BoardList l LEFT JOIN FETCH l.tasks t";
  public static final String  listsByBoard =
      "SELECT l from BoardList l LEFT JOIN FETCH l.tasks t WHERE l.board.id = :boardId";

  private final BoardRepository boardRepository;

  @Autowired
  public BoardListDao(BoardRepository boardRepository) {
    this.boardRepository = boardRepository;
  }

  public BoardList getList(int id) {
    Session session = getSession();
    BoardList boardList = session.get(BoardList.class, id);
    if (boardList == null) {
      throw new BoardListNotFoundException(id);
    }

    TestLogger.log(boardList);
    return boardList;
  }

  public List<BoardList> getLists() {
    Session session = getSession();
    return session.createQuery(
        allLists,
        BoardList.class).getResultList();
  }

  public List<BoardList> getLists(Integer boardId) {
    if (!boardRepository.existsById(boardId)) {
      throw new BoardNotFoundException(boardId);
    }
    Session session = getSession();
    return session.createQuery(
        listsByBoard,
        BoardList.class).setParameter("boardId", boardId).getResultList();
  }





}
