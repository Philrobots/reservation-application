package ca.ulaval.glo4002.reservation.domain;

import java.util.List;

import ca.ulaval.glo4002.reservation.domain.reservation.table.Table;
import ca.ulaval.glo4002.reservation.service.reservation.dto.TableDto;

public class ReservationRequest {
  private final String dinnerDate;
  private final String reservationDate;
  private final String vendorCode;
  private final List<TableDto> tables;

  public ReservationRequest(String dinnerDate, String reservationDate, List<TableDto> tables, String vendorCode) {
    this.dinnerDate = dinnerDate;
    this.reservationDate = reservationDate;
    this.tables = tables;
    this.vendorCode = vendorCode;
  }

  public String getDinnerDate() {
    return dinnerDate;
  }

  public String getReservationDate() {
    return reservationDate;
  }

  public List<TableDto> getTables() {
    return tables;
  }

  public String getVendorCode() {
    return vendorCode;
  }
}
