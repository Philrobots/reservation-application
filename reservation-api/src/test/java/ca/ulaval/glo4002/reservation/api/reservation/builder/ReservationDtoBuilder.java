package ca.ulaval.glo4002.reservation.api.reservation.builder;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.reservation.api.reservation.dto.CustomerApiDto;
import ca.ulaval.glo4002.reservation.api.reservation.dto.ReservationDto;

public class ReservationDtoBuilder {
  private static final String A_DINNER_DATE = "2150-07-21T15:23:20.142Z";
  private final List<CustomerApiDto> customers = new ArrayList<>();
  private String dinnerDate = A_DINNER_DATE;

  public ReservationDtoBuilder withAnyCustomers() {
    customers.add(new CustomerDtoBuilder().build());
    return this;
  }

  public ReservationDto build() {
    ReservationDto reservationDto = new ReservationDto();
    reservationDto.setDinnerDate(dinnerDate);
    reservationDto.setCustomers(customers);
    return reservationDto;
  }
}
