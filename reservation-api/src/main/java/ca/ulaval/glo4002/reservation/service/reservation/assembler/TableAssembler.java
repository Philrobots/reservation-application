package ca.ulaval.glo4002.reservation.service.reservation.assembler;

import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.reservation.api.reservation.dto.TableApiDto;
import ca.ulaval.glo4002.reservation.domain.reservation.customer.Customer;
import ca.ulaval.glo4002.reservation.domain.reservation.table.Table;

public class TableAssembler {
  private final CustomerAssembler customerAssembler;

  public TableAssembler(CustomerAssembler customerAssembler) {
    this.customerAssembler = customerAssembler;
  }

  public Table assembleFromTableDto(TableApiDto tableApiDto) {
    List<Customer> customers = tableApiDto.getCustomers().stream().map(customerAssembler::assembleFromCustomerDto).collect(Collectors.toList());
    return new Table(customers);
  }
}
