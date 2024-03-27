package ru.paskal.MantisManager.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.paskal.MantisManager.models.dto.user.UserDtoForLoad;
import ru.paskal.MantisManager.services.BoardService;
import ru.paskal.MantisManager.services.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

  private final UserService userService;
  private final BoardService boardService;
  private final ModelMapper modelMapper;


  @GetMapping("/{id}")
  public ResponseEntity<UserDtoForLoad> getUser(@PathVariable int id) {
    System.out.println(userService.getOne(id));
    UserDtoForLoad user = modelMapper.map(userService.getOne(id), UserDtoForLoad.class);

    return new ResponseEntity<>(user, HttpStatus.OK);
  }

}
