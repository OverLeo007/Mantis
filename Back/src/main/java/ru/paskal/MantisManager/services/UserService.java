package ru.paskal.MantisManager.services;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
public class UserService {

  private UserRepository repository;

  private final PasswordEncoder passwordEncoder;

  public User getOne(int id) {
    return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
  }

  public Optional<User> getOne(String username) {
    return repository.findByUsername(username);
  }

  public User save(User user) {
    return repository.save(user);
  }

  public void deleteById(int id) {
    repository.deleteById(id);
  }

  public void createNewUser(String username, String password, String email) {
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
