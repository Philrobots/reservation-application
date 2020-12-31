package ca.ulaval.glo4002.reservation.api.mappers;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.reservation.api.reservation.ExceptionResponse;

@Provider
public class CatchInvalidRequestFormatMapper
  implements
    ExceptionMapper<ConstraintViolationException>
{
  private static final int STATUS_CODE = Response.Status.BAD_REQUEST.getStatusCode();
  private static final String ERROR_CODE = "INVALID_FORMAT";
  private static final String ERROR_MESSAGE = "Invalid Format";

  @Override
  public Response toResponse(ConstraintViolationException exception) {
    return Response.status(STATUS_CODE)
                   .entity(new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE))
                   .build();
  }
}
