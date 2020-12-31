package ca.ulaval.glo4002.reservation.service.reservation.assembler;

import ca.ulaval.glo4002.reservation.api.reservation.dto.TableApiDto;
import ca.ulaval.glo4002.reservation.service.reservation.dto.CustomerDto;
import ca.ulaval.glo4002.reservation.service.reservation.dto.TableDto;

import java.util.List;
import java.util.stream.Collectors;

public class TableDtoAssembler {
  private final CustomerDtoAssembler customerDtoAssembler;

  public TableDtoAssembler(CustomerDtoAssembler customerDtoAssembler) {
    this.customerDtoAssembler = customerDtoAssembler;
  }

  public TableDto assemble(TableApiDto tableApiDto) {
    List<CustomerDto> customerDtos = tableApiDto.getCustomers()
                                                .stream()
                                                .map(customerDtoAssembler::assemble)
                                                .collect(Collectors.toList());
    return new TableDto(customerDtos);
  }
}
