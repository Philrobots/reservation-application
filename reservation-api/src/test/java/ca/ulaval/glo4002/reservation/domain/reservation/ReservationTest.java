package ca.ulaval.glo4002.reservation.domain.reservation;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.ulaval.glo4002.reservation.domain.reservation.customer.Customer;
import ca.ulaval.glo4002.reservation.domain.reservation.table.Table;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.builder.CustomerBuilder;
import ca.ulaval.glo4002.reservation.domain.builder.ReservationBuilder;

@ExtendWith(MockitoExtension.class)
public class ReservationTest {

  private static final BigDecimal NO_COST = BigDecimal.ZERO;
  private static final BigDecimal A_TABLE_EXPECTED_PRICE = BigDecimal.valueOf(2500);
  private static final BigDecimal ANOTHER_TABLE_EXPECTED_PRICE = BigDecimal.valueOf(2500);
  private static final BigDecimal PRICE_WITH_TWO_TABLES = BigDecimal.valueOf(5000);
  private static final Map<RestrictionType, Integer> TWO_VEGANS = Collections.singletonMap(RestrictionType.VEGAN,
                                                                                           2);
  private static final RestrictionType ALLERGIES_RESTRICTION = RestrictionType.ALLERGIES;
  private static final RestrictionType NONE_RESTRICTION = RestrictionType.NONE;

  @Mock
  private Table aTable;

  @Mock
  private Table anotherTable;

  @Test
  public void givenAReservationWithEmptyTable_whenGetReservationFees_thenReturnNoCost() {
    // given
    given(aTable.getTableReservationFees()).willReturn(NO_COST);
    Reservation reservation = new ReservationBuilder().withTable(aTable).build();

    // when
    BigDecimal price = reservation.getReservationFees();

    // then
    assertThat(price).isEqualTo(NO_COST);
  }

  @Test
  public void givenAReservationWithATable_whenGetReservationFees_thenReturnPriceAccordingToReservation() {
    // given
    given(aTable.getTableReservationFees()).willReturn(A_TABLE_EXPECTED_PRICE);
    Reservation reservation = new ReservationBuilder().withTable(aTable).build();

    // when
    BigDecimal price = reservation.getReservationFees();

    // then
    assertThat(price).isEqualTo(A_TABLE_EXPECTED_PRICE);
  }

  @Test
  public void givenAReservationWithManyTables_whenGetReservationFees_thenReturnPriceAccordingToReservation() {
    // given
    given(aTable.getTableReservationFees()).willReturn(A_TABLE_EXPECTED_PRICE);
    given(anotherTable.getTableReservationFees()).willReturn(ANOTHER_TABLE_EXPECTED_PRICE);
    Reservation reservation = new ReservationBuilder().withTable(aTable)
                                                      .withTable(anotherTable)
                                                      .build();

    // when
    BigDecimal price = reservation.getReservationFees();

    // then
    assertThat(price).isEqualTo(PRICE_WITH_TWO_TABLES);
  }

  @Test
  public void givenAReservationWithTwoVeganCustomers_whenGetRestrictionTypeCount_thenReturnRestrictionTypeCount() {
    // given
    given(aTable.getRestrictionTypeCount()).willReturn(TWO_VEGANS);
    Reservation reservation = new ReservationBuilder().withTable(aTable).build();

    // when
    Map<RestrictionType, Integer> restrictionTypeCount = reservation.getRestrictionTypeCount();

    // then
    assertThat(restrictionTypeCount.get(RestrictionType.VEGAN)).isEqualTo(2);
  }

  @Test
  public void givenAReservation_whenGetRestrictionTypes_thenReturnRestrictionTypes() {
    // given
    Customer customer = new CustomerBuilder().withRestriction(ALLERGIES_RESTRICTION)
                                             .withRestriction(NONE_RESTRICTION)
                                             .build();
    given(aTable.getCustomers()).willReturn(Collections.singletonList(customer));
    Reservation reservation = new ReservationBuilder().withTable(aTable).build();

    // when
    Set<RestrictionType> restrictionTypes = reservation.getRestrictionTypes();

    // then
    assertThat(restrictionTypes).contains(ALLERGIES_RESTRICTION);
    assertThat(restrictionTypes).contains(NONE_RESTRICTION);
  }

  @Test
  public void givenATableWithTwoCustomers_whenGetNumberOfCustomers_theNumberOfCustomersShouldBeTwo() {
    // given
    Table aTable = givenATableWithTwoCustomers();
    Reservation reservation = new ReservationBuilder().withTable(aTable).build();

    // when
    int numberOfCustomers = reservation.getNumberOfCustomers();

    // then
    assertThat(numberOfCustomers).isEqualTo(2);
  }

  @Test
  public void givenTwoTablesWithTwoCustomersEach_whenGetNumberOfCustomers_thenNumberOfCustomersShouldBeTheTotalOfAllTables() {
    // given
    Table aTable = givenATableWithTwoCustomers();
    Table anotherTable = givenATableWithTwoCustomers();
    Reservation reservation = new ReservationBuilder().withTable(aTable)
                                                      .withTable(anotherTable)
                                                      .build();

    // when
    int numberOfCustomers = reservation.getNumberOfCustomers();

    // then
    assertThat(numberOfCustomers).isEqualTo(4);
  }

  @Test
  public void givenAReservation_whenGetCustomers_thenListOfCostumersIsReturn() {
    // given
    Table table = givenATableWithTwoCustomers();
    Reservation reservation = new ReservationBuilder().withTable(table).build();

    // when
    List<Customer> customers = reservation.getCustomers();

    // then
    assertThat(customers.get(0)).isEqualTo(table.getCustomers().get(0));
    assertThat(customers.get(1)).isEqualTo(table.getCustomers().get(1));
  }

  @Test
  public void givenAReservationWithCustomersWithRestrictions_whenGetCountOfRestriction_thenReturnTheTotalCountOfRestrictions() {
    // given
    given(aTable.getRestrictionTypeCount()).willReturn(Map.of(ALLERGIES_RESTRICTION,
                                                              2,
                                                              NONE_RESTRICTION,
                                                              1));
    given(anotherTable.getRestrictionTypeCount()).willReturn(Map.of(ALLERGIES_RESTRICTION, 4));
    Reservation reservation = new ReservationBuilder().withTable(aTable)
                                                      .withTable(anotherTable)
                                                      .build();

    // when
    int numberOfRestrictions = reservation.getNumberOfRestrictions();

    // then
    assertThat(numberOfRestrictions).isEqualTo(6);
  }

  private Table givenATableWithTwoCustomers() {
    Customer aCustomer = new CustomerBuilder().build();
    Customer anotherCustomer = new CustomerBuilder().build();
    return new Table(List.of(aCustomer, anotherCustomer));
  }
}
