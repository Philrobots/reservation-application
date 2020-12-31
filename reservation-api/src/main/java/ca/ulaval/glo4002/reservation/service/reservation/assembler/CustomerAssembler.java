package ca.ulaval.glo4002.reservation.service.reservation.assembler;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.reservation.api.reservation.dto.CustomerApiDto;
import ca.ulaval.glo4002.reservation.domain.reservation.customer.Customer;
import ca.ulaval.glo4002.reservation.domain.reservation.RestrictionType;

public class CustomerAssembler {
  public Customer assembleFromCustomerDto(CustomerApiDto customerApiDto) {
    Set<RestrictionType> restrictions = customerApiDto.getRestrictions()
                                                      .stream()
                                                      .map(RestrictionType::valueOfName)
                                                      .collect(Collectors.toSet());
    return new Customer(customerApiDto.getName(), restrictions);
  }

  public CustomerApiDto assembleDtoFromCustomer(Customer customer) {
    List<String> restrictions = customer.getRestrictions()
                                        .stream()
                                        .map(RestrictionType::toString).sorted()
                                        .collect(Collectors.toList());
    CustomerApiDto customerApiDto = new CustomerApiDto();
    customerApiDto.setName(customer.getName());
    customerApiDto.setRestrictions(restrictions);
    return customerApiDto;
  }
}
