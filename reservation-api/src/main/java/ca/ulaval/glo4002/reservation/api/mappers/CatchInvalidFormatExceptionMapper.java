package ca.ulaval.glo4002.reservation.api.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.reservation.api.reservation.ExceptionResponse;
import ca.ulaval.glo4002.reservation.api.reservation.exception.InvalidFormatException;

@Provider
public class CatchInvalidFormatExceptionMapper implements ExceptionMapper<InvalidFormatException> {
  private static final int STATUS_CODE = Response.Status.BAD_REQUEST.getStatusCode();

  @Override
  public Response toResponse(InvalidFormatException exception) {
    return Response.status(STATUS_CODE)
                   .entity(new ExceptionResponse(exception.getError(), exception.getDescription()))
                   .build();
  }
}
