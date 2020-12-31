package ca.ulaval.glo4002.reservation.domain.reservation.customer;

import ca.ulaval.glo4002.reservation.domain.reservation.RestrictionType;

import java.math.BigDecimal;
import java.util.Set;

public class Customer {
  private static final BigDecimal BASIC_CUSTOMER_FEES = BigDecimal.valueOf(1000);
  private final String name;
  private final Set<RestrictionType> restrictions;

  public Customer(String name, Set<RestrictionType> restrictions) {
    this.name = name;
    this.restrictions = restrictions;
  }

  public String getName() {
    return name;
  }

  public Set<RestrictionType> getRestrictions() {
    return restrictions;
  }

  public BigDecimal getCustomerFees() {
    return BASIC_CUSTOMER_FEES.add(getAdditionalFees());
  }

  private BigDecimal getAdditionalFees() {
    BigDecimal additionalFees = BigDecimal.ZERO;
    for (RestrictionType restrictionType : restrictions) additionalFees = additionalFees.add(restrictionType.getFees());
    return additionalFees;
  }
}
