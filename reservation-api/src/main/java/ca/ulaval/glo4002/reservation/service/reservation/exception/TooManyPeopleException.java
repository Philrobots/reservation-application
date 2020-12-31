package ca.ulaval.glo4002.reservation.service.reservation.exception;

import ca.ulaval.glo4002.reservation.domain.exception.ReservationException;

public class TooManyPeopleException extends ReservationException {
  private static final String ERROR_CODE = "TOO_MANY_PEOPLE";
  private static final String ERROR_MESSAGE = "The reservation tries to bring a number of people which does not comply with recent government laws.";

  public TooManyPeopleException() {
    super(ERROR_CODE, ERROR_MESSAGE);
  }
}
