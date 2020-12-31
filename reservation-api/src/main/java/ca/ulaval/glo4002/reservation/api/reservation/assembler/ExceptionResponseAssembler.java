package ca.ulaval.glo4002.reservation.api.reservation.assembler;

import ca.ulaval.glo4002.reservation.api.reservation.ExceptionResponse;
import ca.ulaval.glo4002.reservation.domain.exception.ReservationException;

public class ExceptionResponseAssembler {

  public ExceptionResponse assembleExceptionResponseFromException(ReservationException reservationException) {
    return new ExceptionResponse(reservationException.getError(),
                                 reservationException.getDescription());
  }
}
