package ca.ulaval.glo4002.reservation.domain.report;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReportPeriod {
  private final LocalDate startDate;
  private final LocalDate endDate;

  public ReportPeriod(LocalDate startDate, LocalDate endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public List<LocalDate> getAllDaysOfPeriod() {
    return startDate.datesUntil(endDate.plusDays(1)).collect(Collectors.toList());
  }
}
