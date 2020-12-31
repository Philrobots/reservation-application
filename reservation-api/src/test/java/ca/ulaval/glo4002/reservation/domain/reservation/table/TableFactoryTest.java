package ca.ulaval.glo4002.reservation.domain.reservation.table;

import ca.ulaval.glo4002.reservation.domain.reservation.customer.CustomerFactory;
import ca.ulaval.glo4002.reservation.service.reservation.dto.CustomerDto;
import ca.ulaval.glo4002.reservation.service.reservation.dto.TableDto;
import ca.ulaval.glo4002.reservation.service.reservation.exception.InvalidReservationQuantityException;
import ca.ulaval.glo4002.reservation.service.reservation.exception.TooManyPeopleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TableFactoryTest {
  private static final int EXCEEDING_NUMBER_OF_CUSTOMERS_PER_TABLE = 5;
  private static final String ANY_NAME = "any name";
  private static final List<String> ANY_RESTRICTIONS = Collections.emptyList();
  private static final int FOUR_CUSTOMERS = 4;
  private static final int THREE_CUSTOMERS = 3;

  @Mock
  private CustomerFactory customerFactory;

  private TableFactory tableFactory;

  @BeforeEach
  public void setUpTableFactory() {
    tableFactory = new TableFactory(customerFactory);
  }

  @Test
  public void givenEmptyTables_whenValidateTables_thenThrowInvalidReservationQuantityException() {
    // given
    List<TableDto> tableDtos = Collections.emptyList();

    // when
    Executable creatingTables = () -> tableFactory.createTables(tableDtos);

    // then
    assertThrows(InvalidReservationQuantityException.class, creatingTables);
  }

  @Test
  public void givenTableWithEmptyCustomers_whenValidateTables_thenThrowInvalidReservationQuantityException() {
    // given
    List<CustomerDto> customerDtos = Collections.emptyList();
    TableDto tableDto = new TableDto(customerDtos);
    List<TableDto> tableDtos = Collections.singletonList(tableDto);

    // when
    Executable creatingTables = () -> tableFactory.createTables(tableDtos);

    // then
    assertThrows(InvalidReservationQuantityException.class, creatingTables);
  }

  @Test
  public void givenTablesWithExceedingNumberOfCustomersPerTable_whenValidateTables_thenThrowTooManyPeopleException() {
    // given
    TableDto tableDto = new TableDto(givenCustomers(EXCEEDING_NUMBER_OF_CUSTOMERS_PER_TABLE));
    List<TableDto> tableDtos = Collections.singletonList(tableDto);

    // when
    Executable creatingTables = () -> tableFactory.createTables(tableDtos);

    // then
    assertThrows(TooManyPeopleException.class, creatingTables);
  }

  @Test
  public void givenTablesWithTotalNumberOfCustomersExceedingCustomerCapacityPerReservation_whenValidateTables_thenThrowTooManyPeopleException() {
    // given
    TableDto aTableDto = new TableDto(givenCustomers(FOUR_CUSTOMERS));
    TableDto anotherTableDto = new TableDto(givenCustomers(THREE_CUSTOMERS));

    List<TableDto> tableDtos = Arrays.asList(aTableDto, anotherTableDto);

    // when
    Executable creatingTables = () -> tableFactory.createTables(tableDtos);

    // then
    assertThrows(TooManyPeopleException.class, creatingTables);
  }

  private List<CustomerDto> givenCustomers(int numberOfCustomers) {
    List<CustomerDto> customerDtos = new ArrayList<>();
    for (int count = 0; count < numberOfCustomers; count++) {
      customerDtos.add(new CustomerDto(ANY_NAME, ANY_RESTRICTIONS));
    }
    return customerDtos;
  }
}
