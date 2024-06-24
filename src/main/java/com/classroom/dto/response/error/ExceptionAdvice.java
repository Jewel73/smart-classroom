package com.classroom.dto.response.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class ExceptionAdvice {
  //@Value("${drees.stacktrace}")
  boolean stackTrace = false;

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ErrorResponse<List<StackTraceElement>> processAllError(Exception ex) {
    List<StackTraceElement> ele = null;
    if (stackTrace == true) ele = Arrays.asList( ex.getStackTrace() );

    ErrorResponse<List<StackTraceElement>> response = new ErrorResponse<>(ele, ex.getMessage());
    return response;
  }
}