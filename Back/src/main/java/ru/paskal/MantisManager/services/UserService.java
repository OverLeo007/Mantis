package ru.paskal.MantisManager.services;

import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.paskal.MantisManager.exceptions.notCreated.UserNotCreatedException;
import ru.paskal.MantisManager.exceptions.notFound.UserNotFoundException;
import ru.paskal.MantisManager.entities.User;
import ru.paskal.MantisManager.repositories.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {

  private final UserRepository repository;

  private final PasswordEncoder passwordEncoder;

  public User getOne(int id) {
    return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
  }

  public Optional<User> getOne(String username) {
    return repository.findByUsername(username);
  }

  @Transactional
  public User save(User user) {
    return repository.save(user);
  }

  @Transactional
  public void deleteById(int id) {
    repository.deleteById(id);
  }

  @Transactional
  public void createNewUser(String username, String password, String email) {
    log.info("repo" + repository.findAll().stream().map(User::getUsername).collect(Collectors.joining()));
    log.info("created: " + username);
    if (getOne(username).isPresent()) {
      throw new UserNotCreatedException("User with username " + username + " already exists");
    }
    User user = new User();
    user.setUsername(username);
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(password));
    user.setSimpleRole("ROLE_USER");
    save(user);
  }



}
