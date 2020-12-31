package ca.ulaval.glo4002.reservation.domain.report.exception;

import ca.ulaval.glo4002.reservation.domain.exception.ReservationException;

public class InvalidReportTypeException extends ReservationException {
  private static final String ERROR = "INVALID_TYPE";
  private static final String DESCRIPTION = "Type must be either total or unit and must be specified.";

  public InvalidReportTypeException() {
    super(ERROR, DESCRIPTION);
  }
}
