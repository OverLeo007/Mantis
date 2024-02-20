package ru.paskal.MantisManager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.paskal.MantisManager.repositories.CommentRepository;

@Service
@Transactional(readOnly = true)
public class CommentService {

  private CommentRepository repository;

  @Autowired
  public CommentService(CommentRepository repository) {
    this.repository = repository;
  }
}
