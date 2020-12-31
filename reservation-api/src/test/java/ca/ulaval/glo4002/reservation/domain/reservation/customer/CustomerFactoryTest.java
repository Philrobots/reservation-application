package ca.ulaval.glo4002.reservation.domain.reservation.customer;

import ca.ulaval.glo4002.reservation.domain.reservation.InvalidRestrictionException;
import ca.ulaval.glo4002.reservation.domain.reservation.RestrictionType;
import ca.ulaval.glo4002.reservation.service.reservation.dto.CustomerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static com.google.common.truth.Truth.assertThat;


import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerFactoryTest {
  private static final String A_NAME = "name";
  private static final String INVALID_RESTRICTION = "invalid restriction";
  private static final RestrictionType A_RESTRICTION_TYPE = RestrictionType.ALLERGIES;

  private CustomerFactory customerFactory;

  @BeforeEach
  public void setUpCustomerFactory() {
    customerFactory = new CustomerFactory();
  }

  @Test
  public void whenCreate_thenCustomerIsCreated() {
    // given
    CustomerDto customerDto = new CustomerDto(A_NAME,
                                              Collections.singletonList(A_RESTRICTION_TYPE.toString()));

    // when
    Customer customer = customerFactory.create(customerDto);

    // then
    assertThat(customer.getName()).isEqualTo(A_NAME);
    assertThat(customer.getRestrictions()).contains(A_RESTRICTION_TYPE);

  }

  @Test
  public void givenAnInvalidRestriction_whenCreate_thenThrowInvalidRestrictionException() {
    // given
    List<String> restrictionsName = Collections.singletonList(INVALID_RESTRICTION);
    CustomerDto customerDto = new CustomerDto(A_NAME, restrictionsName);

    // when
    Executable creatingCustomer = () -> customerFactory.create(customerDto);

    // then
    assertThrows(InvalidRestrictionException.class, creatingCustomer);
  }
}
