package ca.ulaval.glo4002.reservation.api.reservation.builder;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.reservation.api.reservation.dto.CreateReservationRequestDto;
import ca.ulaval.glo4002.reservation.api.reservation.dto.ReservationDetailsDto;
import ca.ulaval.glo4002.reservation.api.reservation.dto.TableApiDto;

public class CreateReservationRequestDtoBuilder {
  private static final String A_VENDOR_CODE = "vendor code";
  private static final String A_DINNER_DATE = "2150-07-21T15:23:20.142Z";
  private final List<TableApiDto> tables;
  private String vendorCode;
  private String dinnerDate;
  private ReservationDetailsDto reservationDetails;

  public CreateReservationRequestDtoBuilder() {
    vendorCode = A_VENDOR_CODE;
    dinnerDate = A_DINNER_DATE;
    tables = new ArrayList<>();
    reservationDetails = new ReservationDetailsDtoBuilder().build();
  }

  public CreateReservationRequestDtoBuilder withVendorCode(String vendorCode) {
    this.vendorCode = vendorCode;
    return this;
  }

  public CreateReservationRequestDtoBuilder withDinnerDate(String dinnerDate) {
    this.dinnerDate = dinnerDate;
    return this;
  }

  public CreateReservationRequestDtoBuilder withTable(TableApiDto table) {
    tables.add(table);
    return this;
  }

  public CreateReservationRequestDtoBuilder withAnyTable() {
    tables.add(new TableDtoBuilder().withAnyCustomer().build());
    return this;
  }

  public CreateReservationRequestDtoBuilder withReservationDetails(ReservationDetailsDto reservationDetailsDto) {
    this.reservationDetails = reservationDetailsDto;
    return this;
  }

  public CreateReservationRequestDto build() {
    CreateReservationRequestDto createReservationRequestDto = new CreateReservationRequestDto();
    createReservationRequestDto.setVendorCode(vendorCode);
    createReservationRequestDto.setDinnerDate(dinnerDate);
    createReservationRequestDto.setTables(tables);
    createReservationRequestDto.setReservationDetails(reservationDetails);
    return createReservationRequestDto;
  }
}
