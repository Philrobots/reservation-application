package ca.ulaval.glo4002.reservation.domain.reservation.table;

import ca.ulaval.glo4002.reservation.domain.reservation.customer.Customer;
import ca.ulaval.glo4002.reservation.domain.reservation.RestrictionType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {

  private final List<Customer> customers;

  public Table(List<Customer> customers) {
    this.customers = customers;
  }

  public List<Customer> getCustomers() {
    return customers;
  }

  public BigDecimal getTableReservationFees() {
    BigDecimal tableReservationFees = BigDecimal.ZERO;
    for (Customer customer : customers) {
      tableReservationFees = tableReservationFees.add(customer.getCustomerFees());
    }
    return tableReservationFees;
  }

  public Map<RestrictionType, Integer> getRestrictionTypeCount() {
    Map<RestrictionType, Integer> restrictionTypeCount = new HashMap<>();
    customers.forEach(customer -> {
      if (customer.getRestrictions().isEmpty()) {
        incrementNoReservationCount(restrictionTypeCount);
      }
      incrementRestrictionTypeCount(customer, restrictionTypeCount);
    });
    return restrictionTypeCount;
  }

  private void incrementNoReservationCount(Map<RestrictionType, Integer> restrictionTypeCount) {
    if (restrictionTypeCount.containsKey(RestrictionType.NONE)) {
      restrictionTypeCount.replace(RestrictionType.NONE,
                                   restrictionTypeCount.get(RestrictionType.NONE) + 1);
    } else {
      restrictionTypeCount.put(RestrictionType.NONE, 1);
    }
  }

  private void incrementRestrictionTypeCount(Customer customer, Map<RestrictionType, Integer> restrictionTypeCount) {
    customer.getRestrictions().forEach(restrictionType -> {
      if (restrictionTypeCount.containsKey(restrictionType)) {
        restrictionTypeCount.replace(restrictionType,
                restrictionTypeCount.get(restrictionType) + 1);
      } else {
        restrictionTypeCount.put(restrictionType, 1);
      }
    });
  }
}
