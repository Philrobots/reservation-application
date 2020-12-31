package ca.ulaval.glo4002.reservation.domain.reservation.table;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import ca.ulaval.glo4002.reservation.domain.reservation.RestrictionType;
import ca.ulaval.glo4002.reservation.domain.reservation.customer.Customer;
import ca.ulaval.glo4002.reservation.domain.reservation.table.Table;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.builder.CustomerBuilder;
import ca.ulaval.glo4002.reservation.domain.builder.TableBuilder;

@ExtendWith(MockitoExtension.class)
public class TableTest {
  private static final BigDecimal NO_PRICE = BigDecimal.ZERO;
  private static final BigDecimal A_TABLE_EXPECTED_PRICE = BigDecimal.valueOf(1000);
  private static final BigDecimal ANOTHER_TABLE_EXPECTED_PRICE = BigDecimal.valueOf(3000);
  private static final BigDecimal TWO_TABLE_EXPECTED_PRICE = BigDecimal.valueOf(4000);
  private static final int TWO_VEGANS = 2;
  private static final int ONE_CUSTOMER_WITH_ALLERGIES = 1;
  private static final int ONE_CUSTOMER_WITH_NO_RESTRICTION = 1;

  @Mock
  private Customer aCustomer;

  @Mock
  private Customer anotherCustomer;

  @Test
  public void givenTableWithoutCustomer_whenGetPrice_thenReturnNoCost() {
    // given
    Table table = new TableBuilder().build();

    // when
    BigDecimal price = table.getTableReservationFees();

    // then
    assertThat(price).isEqualTo(NO_PRICE);
  }

  @Test
  public void givenTableWithACustomer_whenGetPrice_thenReturnCustomerPrice() {
    // given
    given(aCustomer.getCustomerFees()).willReturn(A_TABLE_EXPECTED_PRICE);
    Table table = new TableBuilder().withCustomer(aCustomer).build();

    // when
    BigDecimal price = table.getTableReservationFees();

    // then
    assertThat(price).isEqualTo(A_TABLE_EXPECTED_PRICE);
  }

  @Test
  public void givenTableWithCustomers_whenGetPrice_thenReturnTotalPrice() {
    // given
    given(aCustomer.getCustomerFees()).willReturn(A_TABLE_EXPECTED_PRICE);
    given(anotherCustomer.getCustomerFees()).willReturn(ANOTHER_TABLE_EXPECTED_PRICE);
    Table table = new TableBuilder().withCustomer(aCustomer).withCustomer(anotherCustomer).build();

    // when
    BigDecimal totalPrice = table.getTableReservationFees();

    // then
    assertThat(totalPrice).isEqualTo(TWO_TABLE_EXPECTED_PRICE);
  }

  @Test
  public void givenCustomersWithRestrictions_whenGetRestrictionTypeCount_thenReturnNumberOfCustomersPerRestriction() {
    // given
    Customer aVeganCustomer = new CustomerBuilder().withRestriction(RestrictionType.VEGAN).build();
    Customer anotherVeganCustomer = new CustomerBuilder().withRestriction(RestrictionType.VEGAN)
                                                         .build();
    Customer aCustomerWithAllergies = new CustomerBuilder().withRestriction(RestrictionType.ALLERGIES)
                                                           .build();
    Customer aCustomerWithNoRestriction = new CustomerBuilder().build();
    Table table = new TableBuilder().withCustomers(Arrays.asList(aVeganCustomer,
                                                                 anotherVeganCustomer,
                                                                 aCustomerWithAllergies,
                                                                 aCustomerWithNoRestriction))
                                    .build();

    // when
    Map<RestrictionType, Integer> map = table.getRestrictionTypeCount();

    // then
    assertThat(map.get(RestrictionType.VEGAN)).isEqualTo(TWO_VEGANS);
    assertThat(map.get(RestrictionType.ALLERGIES)).isEqualTo(ONE_CUSTOMER_WITH_ALLERGIES);
    assertThat(map.get(RestrictionType.NONE)).isEqualTo(ONE_CUSTOMER_WITH_NO_RESTRICTION);
  }
}
