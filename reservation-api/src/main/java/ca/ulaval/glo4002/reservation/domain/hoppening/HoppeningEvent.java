package ca.ulaval.glo4002.reservation.domain.hoppening;

import ca.ulaval.glo4002.reservation.domain.date.Period;

import java.time.format.DateTimeFormatter;

public class HoppeningEvent {

  private static final String PATTERN_FORMAT = "MMMM dd YYYY";

  private Period dinnerDinerPeriod;
  private Period reservationDinerPeriod;

  public HoppeningEvent(Period dinnerDinerPeriod, Period reservationDinerPeriod) {
    this.dinnerDinerPeriod = dinnerDinerPeriod;
    this.reservationDinerPeriod = reservationDinerPeriod;
  }

  public void configureHoppening(HoppeningConfigurationRequest hoppeningConfigurationRequest) {
    dinnerDinerPeriod = hoppeningConfigurationRequest.getDinnerDinerPeriod();
    reservationDinerPeriod = hoppeningConfigurationRequest.getReservationDinerPeriod();
  }

  public Period getDinnerDinerPeriod() {
    return dinnerDinerPeriod;
  }

  public String getPeriodStart() {
    return dinnerDinerPeriod.getStartDate().format(DateTimeFormatter.ofPattern(PATTERN_FORMAT));
  }

  public String getPeriodEnd() {
    return dinnerDinerPeriod.getEndDate().format(DateTimeFormatter.ofPattern(PATTERN_FORMAT));
  }

  public Period getReservationDinerPeriod() {
    return reservationDinerPeriod;
  }
}
