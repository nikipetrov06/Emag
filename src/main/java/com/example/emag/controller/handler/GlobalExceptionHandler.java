package com.example.emag.controller.handler;

import com.example.emag.dto.ErrorDto;
import com.example.emag.exceptions.AuthorizationException;
import com.example.emag.exceptions.BadRequestException;
import com.example.emag.exceptions.NotFoundException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorDto handleNotFound(Exception exception) {
    log.error(exception.getMessage(), exception);
    return new ErrorDto(
        exception.getMessage(),
        HttpStatus.NOT_FOUND.value(),
        LocalDateTime.now(),
        exception.getClass().getName());
  }

  @ExceptionHandler(AuthorizationException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErrorDto unauthorized(Exception exception) {
    log.error(exception.getMessage(), exception);
    return new ErrorDto(
        exception.getMessage(),
        HttpStatus.UNAUTHORIZED.value(),
        LocalDateTime.now(),
        exception.getClass().getName());
  }

  @ExceptionHandler(SQLException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorDto handleSqlExceptions(Exception exception) {
    log.error(exception.getMessage(), exception);
    return new ErrorDto(
        exception.getMessage(),
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        LocalDateTime.now(),
        exception.getClass().getName());
  }

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDto handleBadRequests(Exception exception) {
    log.error(exception.getMessage(), exception);
    return new ErrorDto(
        exception.getMessage(),
        HttpStatus.BAD_REQUEST.value(),
        LocalDateTime.now(),
        exception.getClass().getName());
  }

  @ExceptionHandler(MessagingException.class)
  @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
  public ErrorDto handleEmailException(Exception exception) {
    log.error(exception.getMessage(), exception);
    return new ErrorDto(
        exception.getMessage(),
        HttpStatus.SERVICE_UNAVAILABLE.value(),
        LocalDateTime.now(),
        exception.getClass().getName());
  }

  @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDto handleSqlConstraintException(Exception exception) {
    log.error(exception.getMessage(), exception);
    return new ErrorDto(
        exception.getMessage(),
        HttpStatus.BAD_REQUEST.value(),
        LocalDateTime.now(),
        exception.getClass().getName());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
    Map<String, String> errorMessages = new HashMap<>();
    BindingResult result = exception.getBindingResult();
    List<ObjectError> errors = result.getAllErrors();
    for (ObjectError error : errors) {
      String fieldError = ((FieldError) error).getField();
      errorMessages.put(fieldError + " ", " " + error.getDefaultMessage());
    }
    log.error(exception.getMessage(), exception);
    return new ErrorDto(
        errorMessages.toString(),
        HttpStatus.BAD_REQUEST.value(),
        LocalDateTime.now(),
        exception.getClass().getName());
  }
}
