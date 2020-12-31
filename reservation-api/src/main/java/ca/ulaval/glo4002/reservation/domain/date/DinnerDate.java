package ca.ulaval.glo4002.reservation.domain.date;

import java.time.LocalDateTime;

public class DinnerDate implements Date {
  private final LocalDateTime dinnerDate;

  public DinnerDate(LocalDateTime dinnerDate) {
    this.dinnerDate = dinnerDate;
  }

  public LocalDateTime getLocalDateTime() {
    return dinnerDate;
  }
}