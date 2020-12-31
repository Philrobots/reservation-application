package ca.ulaval.glo4002.reservation.domain.hoppening;

import ca.ulaval.glo4002.reservation.domain.date.DinerPeriod;
import ca.ulaval.glo4002.reservation.domain.date.Period;

public class HoppeningConfigurationRequest {
  private final Period dinnerDinerPeriod;
  private final Period reservationDinerPeriod;

  public HoppeningConfigurationRequest(DinerPeriod dinnerEventDinerPeriod, DinerPeriod reservationEventDinerPeriod) {
    this.dinnerDinerPeriod = dinnerEventDinerPeriod;
    this.reservationDinerPeriod = reservationEventDinerPeriod;
  }

  public Period getDinnerDinerPeriod() {
    return this.dinnerDinerPeriod;
  }

  public Period getReservationDinerPeriod() {
    return this.reservationDinerPeriod;
  }
}
