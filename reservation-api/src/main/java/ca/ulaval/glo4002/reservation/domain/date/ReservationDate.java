package ca.ulaval.glo4002.reservation.domain.date;

import java.time.LocalDateTime;

public class ReservationDate implements Date {
  private final LocalDateTime reservationDate;

  public ReservationDate(LocalDateTime reservationDate) {
    this.reservationDate = reservationDate;
  }

  public LocalDateTime getLocalDateTime() {
    return reservationDate;
  }
}
