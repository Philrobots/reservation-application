package ca.ulaval.glo4002.reservation.api.report.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "date", "chefs", "totalPrice" })
public class ChefReportInformationDto {
  private final String date;
  private final List<String> chefs;
  private final BigDecimal totalPrice;

  public ChefReportInformationDto(String date, List<String> chefs, BigDecimal totalPrice) {
    this.date = date;
    this.chefs = chefs;
    this.totalPrice = totalPrice;
  }

  public String getDate() {
    return date;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public List<String> getChefs() {
    return chefs;
  }
}