package ca.ulaval.glo4002.reservation.api.reservation.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateReservationRequestDto {
  @NotNull
  private String vendorCode;

  @NotNull
  private String dinnerDate;

  @Valid
  @NotNull
  private List<TableApiDto> tables;

  @Valid
  @NotNull
  @JsonProperty("from")
  private ReservationDetailsDto reservationDetails;

  public String getVendorCode() {
    return vendorCode;
  }

  public void setVendorCode(String vendorCode) {
    this.vendorCode = vendorCode;
  }

  public String getDinnerDate() {
    return dinnerDate;
  }

  public void setDinnerDate(String dinnerDate) {
    this.dinnerDate = dinnerDate;
  }

  public List<TableApiDto> getTables() {
    return tables;
  }

  public void setTables(List<TableApiDto> tables) {
    this.tables = tables;
  }

  public ReservationDetailsDto getReservationDetails() {
    return reservationDetails;
  }

  public void setReservationDetails(ReservationDetailsDto reservationDetailsDto) {
    this.reservationDetails = reservationDetailsDto;
  }
}
