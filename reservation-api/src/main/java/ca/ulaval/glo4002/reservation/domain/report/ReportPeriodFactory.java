package ca.ulaval.glo4002.reservation.domain.report;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import ca.ulaval.glo4002.reservation.domain.date.Period;
import ca.ulaval.glo4002.reservation.domain.report.exception.InvalidReportDateException;

public class ReportPeriodFactory {
  private static final String PATTERN_FORMAT = "MMMM dd YYYY";

  public ReportPeriod create(LocalDate reportPeriodStartDate,
                             LocalDate reportPeriodEndDate,
                             Period dinnerDinerPeriod)
  {
    if (areDatesInvalid(reportPeriodStartDate, reportPeriodEndDate, dinnerDinerPeriod)) {
      throw new InvalidReportDateException(dinnerDinerPeriod.getStartDate()
                                                       .format(DateTimeFormatter.ofPattern(PATTERN_FORMAT)),
                                           dinnerDinerPeriod.getEndDate()
                                                       .format(DateTimeFormatter.ofPattern(PATTERN_FORMAT)));
    }
    return new ReportPeriod(reportPeriodStartDate, reportPeriodEndDate);
  }

  private boolean areDatesInvalid(LocalDate startDate, LocalDate endDate, Period dinnerDinerPeriod) {
    return !dinnerDinerPeriod.isWithinPeriod(startDate) || !dinnerDinerPeriod.isWithinPeriod(endDate)
           || isStartDateAfterEndDate(startDate, endDate);
  }

  private boolean isStartDateAfterEndDate(LocalDate startDate, LocalDate endDate) {
    return startDate.isAfter(endDate);
  }
}
