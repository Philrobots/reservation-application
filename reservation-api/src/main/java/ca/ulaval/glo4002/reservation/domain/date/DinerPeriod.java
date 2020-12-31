package ca.ulaval.glo4002.reservation.domain.date;

import java.time.LocalDate;

public class DinerPeriod implements Period {
  private final LocalDate startDate;
  private final LocalDate endDate;

  public DinerPeriod(LocalDate startDate, LocalDate endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public boolean isWithinPeriod(LocalDate date) {
    return !(date.isBefore(this.startDate) || date.isAfter(this.endDate));
  }
}
