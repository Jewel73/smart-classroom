package com.classroom.web.payload.response;

import com.classroom.enums.OperationStatus;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * This class models the format of the login response produced.
 *
 * @author Md Jewel
 * @version 1.0
 * @since 1.0
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LogoutResponse implements Serializable {
  @Serial private static final long serialVersionUID = -133371944006730313L;

  private String message = "Logout successful. Tokens are removed from cookie.";

  @NonNull private OperationStatus status;
}
