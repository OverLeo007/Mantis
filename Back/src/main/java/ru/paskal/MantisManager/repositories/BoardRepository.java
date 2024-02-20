package ru.paskal.MantisManager.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.paskal.MantisManager.models.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
  List<Board> findByUsersId(Integer id);
}
