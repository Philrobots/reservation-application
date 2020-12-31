package ca.ulaval.glo4002.reservation.service.reservation.exception;

import ca.ulaval.glo4002.reservation.domain.exception.ReservationException;

public class InvalidReservationQuantityException extends ReservationException {
  private static final String ERROR_CODE = "INVALID_RESERVATION_QUANTITY";
  private static final String ERROR_MESSAGE = "Reservations must include tables and customers";

  public InvalidReservationQuantityException() {
    super(ERROR_CODE, ERROR_MESSAGE);
  }
}
