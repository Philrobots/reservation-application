package ca.ulaval.glo4002.reservation.api.reservation.builder;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.reservation.api.reservation.dto.CustomerApiDto;
import ca.ulaval.glo4002.reservation.api.reservation.dto.TableApiDto;

public class TableDtoBuilder {
  private final List<CustomerApiDto> customers = new ArrayList<>();

  public TableDtoBuilder withCustomer(CustomerApiDto customer) {
    customers.add(customer);
    return this;
  }

  public TableDtoBuilder withAnyCustomer() {
    customers.add(new CustomerDtoBuilder().build());
    return this;
  }

  public TableApiDto build() {
    TableApiDto tableApiDto = new TableApiDto();
    tableApiDto.setCustomers(customers);
    return tableApiDto;
  }
}