package ca.ulaval.glo4002.reservation.domain.builder;

import java.util.HashSet;
import java.util.Set;

import ca.ulaval.glo4002.reservation.domain.reservation.customer.Customer;
import ca.ulaval.glo4002.reservation.domain.reservation.RestrictionType;

public class CustomerBuilder {
  private static final String A_NAME = "Johnny";

  private final Set<RestrictionType> restrictions = new HashSet<>();

  public CustomerBuilder withRestriction(RestrictionType restriction) {
    restrictions.add(restriction);
    return this;
  }

  public CustomerBuilder withAnyRestriction() {
    restrictions.add(RestrictionType.VEGAN);
    return this;
  }

  public Customer build() {
    return new Customer(A_NAME, restrictions);
  }
}
