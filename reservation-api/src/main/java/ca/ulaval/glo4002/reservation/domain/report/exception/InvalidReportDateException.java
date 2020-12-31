package ca.ulaval.glo4002.reservation.domain.report.exception;

import ca.ulaval.glo4002.reservation.domain.exception.ReservationException;

public class InvalidReportDateException extends ReservationException {
  private static final String ERROR = "INVALID_DATE";
  private static final String DESCRIPTION = "Dates should be between %s and %s and must be specified.";

  public InvalidReportDateException(String startDate, String endDate) {
    super(ERROR, String.format(DESCRIPTION, startDate, endDate));
  }
}
