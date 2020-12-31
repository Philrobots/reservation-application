package ca.ulaval.glo4002.reservation.api.reservation.dto;

import javax.validation.constraints.NotNull;

public class CountryDto {
  @NotNull
  private String code;

  @NotNull
  private String fullname;

  @NotNull
  private String currency;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String name) {
    this.fullname = name;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }
}
