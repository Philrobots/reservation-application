package ca.ulaval.glo4002.reservation.service.reservation.exception;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import ca.ulaval.glo4002.reservation.domain.exception.ReservationException;

public class InvalidReservationDateException extends ReservationException {
  private static final String PATTERN_FORMAT = "MMMM dd YYYY";
  private static final String ERROR_CODE = "INVALID_RESERVATION_DATE";
  private static final String ERROR_MESSAGE = "Reservation date should be between %s and %s";

  public InvalidReservationDateException(LocalDate startDate, LocalDate endDate) {
    super(ERROR_CODE,
          String.format(ERROR_MESSAGE,
                        startDate.format(DateTimeFormatter.ofPattern(PATTERN_FORMAT)),
                        endDate.format(DateTimeFormatter.ofPattern(PATTERN_FORMAT))));
  }
}
