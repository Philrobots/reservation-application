package ca.ulaval.glo4002.reservation.api.reservation.builder;

import ca.ulaval.glo4002.reservation.api.reservation.dto.CountryDto;
import ca.ulaval.glo4002.reservation.api.reservation.dto.ReservationDetailsDto;

public class ReservationDetailsDtoBuilder {
  private static final String A_RESERVATION_DATE = "2150-03-14T01:23:20.142Z";

  private CountryDto countryDto = new CountryDto();
  private String reservationDate = A_RESERVATION_DATE;

  public ReservationDetailsDtoBuilder withReservationDate(String reservationDate) {
    this.reservationDate = reservationDate;
    return this;
  }

  public ReservationDetailsDto build() {
    ReservationDetailsDto reservationDetailsDto = new ReservationDetailsDto();
    reservationDetailsDto.setCountry(this.countryDto);
    reservationDetailsDto.setReservationDate(this.reservationDate);
    return reservationDetailsDto;
  }
}
