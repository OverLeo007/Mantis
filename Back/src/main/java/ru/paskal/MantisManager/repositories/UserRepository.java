package ru.paskal.MantisManager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.paskal.MantisManager.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


}
