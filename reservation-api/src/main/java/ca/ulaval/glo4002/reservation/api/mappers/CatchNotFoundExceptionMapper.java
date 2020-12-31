package ca.ulaval.glo4002.reservation.api.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.reservation.api.reservation.ExceptionResponse;
import ca.ulaval.glo4002.reservation.domain.exception.NotFoundException;

@Provider
public class CatchNotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
  private static final int ERROR_STATUS = Response.Status.NOT_FOUND.getStatusCode();

  @Override
  public Response toResponse(NotFoundException exception) {
    return Response.status(ERROR_STATUS)
                   .entity(new ExceptionResponse(exception.getError(), exception.getDescription()))
                   .build();
  }
}
