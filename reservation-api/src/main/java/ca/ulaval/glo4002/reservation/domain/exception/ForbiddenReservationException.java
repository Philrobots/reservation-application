package ca.ulaval.glo4002.reservation.domain.exception;

public class ForbiddenReservationException extends ReservationException {
  public static final String ERROR_CODE = "TOO_PICKY";
  public static final String ERROR_MESSAGE = "You seem to be too picky and now, you cannot make a reservation for this date.";

  public ForbiddenReservationException() {
    super(ERROR_CODE, ERROR_MESSAGE);
  }

}
