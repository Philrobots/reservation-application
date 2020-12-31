package ca.ulaval.glo4002.reservation.service.reservation.assembler;

import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.reservation.api.reservation.dto.CreateReservationRequestDto;
import ca.ulaval.glo4002.reservation.domain.ReservationRequest;
import ca.ulaval.glo4002.reservation.domain.reservation.table.Table;
import ca.ulaval.glo4002.reservation.service.reservation.dto.TableDto;

public class ReservationRequestAssembler {
  private final TableDtoAssembler tableDtoAssembler;

  public ReservationRequestAssembler(TableDtoAssembler tableDtoAssembler) {
    this.tableDtoAssembler = tableDtoAssembler;
  }

  public ReservationRequest assemble(CreateReservationRequestDto createReservationRequestDto) {
    String dinnerDate = createReservationRequestDto.getDinnerDate();
    String vendorCode = createReservationRequestDto.getVendorCode();
    String reservationDate = createReservationRequestDto.getReservationDetails()
            .getReservationDate();
    List<TableDto> tableDtos = createReservationRequestDto.getTables()
            .stream()
            .map(tableDtoAssembler::assemble)
            .collect(Collectors.toList());
    return new ReservationRequest(dinnerDate, reservationDate, tableDtos, vendorCode);
  }
}
