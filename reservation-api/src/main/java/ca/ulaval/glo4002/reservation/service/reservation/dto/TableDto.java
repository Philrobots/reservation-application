package ca.ulaval.glo4002.reservation.service.reservation.dto;

import java.util.List;

public class TableDto {
  private final List<CustomerDto> customerDtos;

  public TableDto(List<CustomerDto> customerDtos) {
    this.customerDtos = customerDtos;
  }

  public List<CustomerDto> getCustomerObjects() {
    return customerDtos;
  }
}
