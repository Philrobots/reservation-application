package ca.ulaval.glo4002.reservation.domain.builder;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.reservation.domain.reservation.customer.Customer;
import ca.ulaval.glo4002.reservation.domain.reservation.table.Table;

public class TableBuilder {
  private final List<Customer> customers = new ArrayList<>();

  public TableBuilder withCustomer(Customer customer) {
    customers.add(customer);
    return this;
  }

  public TableBuilder withCustomers(List<Customer> customers) {
    this.customers.addAll(customers);
    return this;
  }

  public Table build() {
    return new Table(customers);
  }
}
