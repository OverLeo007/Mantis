package ru.paskal.MantisManager.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

public abstract class Dao {
  @PersistenceContext
  private EntityManager entityManager;

  protected Session getSession() {
    return entityManager.unwrap(Session.class);
  }

}
