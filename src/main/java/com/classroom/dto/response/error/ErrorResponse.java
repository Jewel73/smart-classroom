package com.classroom.dto.response.error;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

//Actual Response body
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse<T> {
  private ErrorDTO<T> error;

  public ErrorResponse(T object, String message) {
    error = new ErrorDTO<T>(object, message);
  }

}