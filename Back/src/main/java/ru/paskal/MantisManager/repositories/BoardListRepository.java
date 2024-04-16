package ru.paskal.MantisManager.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.paskal.MantisManager.entities.BoardList;

@Repository
public interface BoardListRepository extends JpaRepository<BoardList, Integer> {

  List<BoardList> findByBoardId(Integer id);


  @Query("SELECT l.board.id FROM BoardList l WHERE l.id = :listId")
  Optional<Integer> findBoardIdByListId(@Param("listId") Integer listId);
}
