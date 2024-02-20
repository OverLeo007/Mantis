package ru.paskal.MantisManager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.paskal.MantisManager.models.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Integer> {

}
