package com.classroom.dto.response.error;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDTO<T> implements java.io.Serializable {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private T body;
  private String message;

  public ErrorDTO(T body, String message) {
    this.body = body;
    this.message = message;
  }

}