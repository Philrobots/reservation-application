package ca.ulaval.glo4002.reservation.domain.reservation.table;

import ca.ulaval.glo4002.reservation.domain.reservation.customer.CustomerFactory;
import ca.ulaval.glo4002.reservation.domain.reservation.customer.Customer;
import ca.ulaval.glo4002.reservation.service.reservation.dto.TableDto;
import ca.ulaval.glo4002.reservation.service.reservation.exception.InvalidReservationQuantityException;
import ca.ulaval.glo4002.reservation.service.reservation.exception.TooManyPeopleException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TableFactory {
  private static final int MAX_NUMBER_OF_CUSTOMERS_PER_TABLE = 4;
  private static final int MAX_NUMBER_OF_CUSTOMERS_PER_RESERVATION = 6;

  private final CustomerFactory customerFactory;

  public TableFactory(CustomerFactory customerFactory) {
    this.customerFactory = customerFactory;
  }

  public List<Table> createTables(List<TableDto> tableDtos) {
    validateCovidRegulationRules(tableDtos);
    List<Table> tables = new ArrayList<>();
    for (TableDto tableDto : tableDtos) {
      tables.add(createTable(tableDto));
    }
    return tables;
  }

  private Table createTable(TableDto tableDto) {
    List<Customer> customers = tableDto.getCustomerObjects()
            .stream()
            .map(customerFactory::create)
            .collect(Collectors.toList());
    return new Table(customers);
  }

  private void validateCovidRegulationRules(List<TableDto> tableDtos) {
    if (tableDtos.isEmpty()) {
      throw new InvalidReservationQuantityException();
    }
    validateNumberOfCustomers(tableDtos);
  }

  private void validateNumberOfCustomers(List<TableDto> tables) {
    int totalNumberOfCustomers = 0;
    for (TableDto table : tables) {
      if (table.getCustomerObjects().size() > MAX_NUMBER_OF_CUSTOMERS_PER_TABLE) {
        throw new TooManyPeopleException();
      }
      if (table.getCustomerObjects().isEmpty()) {
        throw new InvalidReservationQuantityException();
      }
      totalNumberOfCustomers += table.getCustomerObjects().size();
    }
    validateMaxNumberOfCustomersPerReservation(totalNumberOfCustomers);
  }

  private void validateMaxNumberOfCustomersPerReservation(int totalNumberOfCustomers) {
    if (totalNumberOfCustomers > MAX_NUMBER_OF_CUSTOMERS_PER_RESERVATION) {
      throw new TooManyPeopleException();
    }
  }
}
