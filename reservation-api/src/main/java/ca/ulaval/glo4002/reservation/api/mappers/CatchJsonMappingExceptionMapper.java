package ca.ulaval.glo4002.reservation.api.mappers;

import javax.annotation.Priority;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.JsonMappingException;

import ca.ulaval.glo4002.reservation.api.reservation.ExceptionResponse;

@Priority(1)
@Provider
public class CatchJsonMappingExceptionMapper implements ExceptionMapper<JsonMappingException> {
  private static final int STATUS_CODE = Response.Status.BAD_REQUEST.getStatusCode();
  private static final String ERROR_CODE = "INVALID_FORMAT";
  private static final String ERROR_MESSAGE = "Invalid Format";

  @Override
  public Response toResponse(JsonMappingException exception) {
    return Response.status(STATUS_CODE)
                   .entity(new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE))
                   .build();
  }
}
