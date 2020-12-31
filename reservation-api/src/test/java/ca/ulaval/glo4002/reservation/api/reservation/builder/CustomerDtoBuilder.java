package ca.ulaval.glo4002.reservation.api.reservation.builder;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.reservation.api.reservation.dto.CustomerApiDto;

public class CustomerDtoBuilder {
  private static final String A_NAME = "Elon king Musk";

  private final List<String> restrictions = new ArrayList<>();
  private final String name = A_NAME;

  public CustomerDtoBuilder withRestriction(String restriction) {
    restrictions.add(restriction);
    return this;
  }

  public CustomerApiDto build() {
    CustomerApiDto customerApiDto = new CustomerApiDto();
    customerApiDto.setName(name);
    customerApiDto.setRestrictions(restrictions);
    return customerApiDto;
  }
}
