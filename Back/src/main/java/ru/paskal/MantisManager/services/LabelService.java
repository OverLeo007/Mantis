package ru.paskal.MantisManager.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.paskal.MantisManager.models.Label;
import ru.paskal.MantisManager.repositories.LabelRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LabelService {

  private LabelRepository repository;

}
