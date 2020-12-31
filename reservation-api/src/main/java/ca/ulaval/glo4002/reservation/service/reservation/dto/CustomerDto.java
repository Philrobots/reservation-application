package ca.ulaval.glo4002.reservation.service.reservation.dto;

import java.util.List;

public class CustomerDto {
  private final String name;
  private final List<String> restrictions;

  public CustomerDto(String name, List<String> restrictions) {
    this.name = name;
    this.restrictions = restrictions;
  }

  public String getName() {
    return name;
  }

  public List<String> getRestrictions() {
    return restrictions;
  }
}
