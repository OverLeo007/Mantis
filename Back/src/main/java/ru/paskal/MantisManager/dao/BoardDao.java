package ru.paskal.MantisManager.dao;

import java.util.List;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.paskal.MantisManager.exceptions.notFound.BoardNotFoundException;
import ru.paskal.MantisManager.models.Board;
import ru.paskal.MantisManager.models.BoardList;
import ru.paskal.MantisManager.models.User;
import ru.paskal.MantisManager.utils.TestLogger;

@Component
public class BoardDao extends Dao{

  public static final String listsFromBoard =
      "SELECT l FROM BoardList l LEFT JOIN FETCH l.tasks t  WHERE l.board = :board";
  public static final String usersFromBoard =
      "SELECT u FROM User u JOIN u.boards b WHERE b.id = :boardId";

  public static final String listPositionsFromBoard =
      "SELECT l.listPosition FROM Board b LEFT JOIN b.lists l "
          + "WHERE b.id = :boardId ORDER BY l.listPosition desc";


  @Transactional(readOnly = true)
  public Board getBoard(int id) {
    Session session = getSession();

    Board board = session.get(Board.class, id);

    if (board == null) {
      throw new BoardNotFoundException(id);
    }
    List<BoardList> lists = session.createQuery(
        listsFromBoard,
        BoardList.class
    ).setParameter("board", board).getResultList();


    List<User> users = session.createQuery(
        usersFromBoard,
        User.class
    ).setParameter("boardId", id).getResultList();
    board.setLists(lists);
    board.setUsers(users);

    return board;
  }


  @Transactional(readOnly = true)
  public Integer getNewPosition(int boardId) {
    Session session = getSession();
    List<Integer> usedPos = session.createQuery(
        listPositionsFromBoard,
            Integer.class
        ).setParameter("boardId", boardId).getResultList();
    if (usedPos.get(0) == null) {
      return 0;
    }
    TestLogger.log("not null -> " + usedPos);

    return usedPos.get(0) + 1;
  }

}
