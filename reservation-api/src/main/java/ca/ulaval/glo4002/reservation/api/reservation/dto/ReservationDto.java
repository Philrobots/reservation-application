package ca.ulaval.glo4002.reservation.api.reservation.dto;

import java.math.BigDecimal;
import java.util.List;

public class ReservationDto {
  private String dinnerDate;
  private BigDecimal reservationPrice;
  private List<CustomerApiDto> customers;

  public String getDinnerDate() {
    return dinnerDate;
  }

  public void setDinnerDate(String dinnerDate) {
    this.dinnerDate = dinnerDate;
  }

  public BigDecimal getReservationPrice() {
    return reservationPrice;
  }

  public void setReservationPrice(BigDecimal reservationPrice) {
    this.reservationPrice = reservationPrice;
  }

  public List<CustomerApiDto> getCustomers() {
    return customers;
  }

  public void setCustomers(List<CustomerApiDto> customers) {
    this.customers = customers;
  }
}
