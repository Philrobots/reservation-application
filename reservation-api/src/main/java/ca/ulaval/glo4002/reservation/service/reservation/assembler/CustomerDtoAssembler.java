package ca.ulaval.glo4002.reservation.service.reservation.assembler;

import ca.ulaval.glo4002.reservation.api.reservation.dto.CustomerApiDto;
import ca.ulaval.glo4002.reservation.service.reservation.dto.CustomerDto;

public class CustomerDtoAssembler {
  public CustomerDto assemble(CustomerApiDto customerApiDto) {
    return new CustomerDto(customerApiDto.getName(), customerApiDto.getRestrictions());
  }
}
