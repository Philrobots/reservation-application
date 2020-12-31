package ca.ulaval.glo4002.reservation.api.configuration.exception;

import ca.ulaval.glo4002.reservation.domain.exception.ReservationException;

public class InvalidDateException extends ReservationException {
  private static final String ERROR = "INVALID_DATE";
  private static final String DESCRIPTION = "Invalid dates, please use the format yyyy-mm-dd";

  public InvalidDateException() {
    super(ERROR, DESCRIPTION);
  }
}
