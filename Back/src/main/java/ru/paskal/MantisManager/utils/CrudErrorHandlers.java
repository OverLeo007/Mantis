package ru.paskal.MantisManager.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.paskal.MantisManager.exceptions.notCreated.ModelNotCreatedException;
import ru.paskal.MantisManager.exceptions.notDeleted.ModelNotDeletedException;
import ru.paskal.MantisManager.exceptions.notFound.ModelNotFoundException;
import ru.paskal.MantisManager.exceptions.notUpdated.ModelNotUpdatedException;
import ru.paskal.MantisManager.exceptions.responses.ErrorResponse;

//@Controller
@Deprecated
public class CrudErrorHandlers
    <
    C extends ModelNotCreatedException,
    R extends ModelNotFoundException,
    U extends ModelNotUpdatedException,
    D extends ModelNotDeletedException
        > {

  @ExceptionHandler
  private ResponseEntity<ErrorResponse> handleCreateException(C e) {
    return new ResponseEntity<>(new ErrorResponse(e), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  private ResponseEntity<ErrorResponse> handleReadException(R e) {
    return new ResponseEntity<>(new ErrorResponse(e), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler
  private ResponseEntity<ErrorResponse> handleUpdateException(U e) {
    return new ResponseEntity<>(new ErrorResponse(e), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  private ResponseEntity<ErrorResponse> handleDeleteException(D e) {
    return new ResponseEntity<>(new ErrorResponse(e), HttpStatus.BAD_REQUEST);
  }

//  @ExceptionHandler
//  private ResponseEntity<ErrorResponse> handleDeleteException(Exception e) {
//    return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
//  }

}
