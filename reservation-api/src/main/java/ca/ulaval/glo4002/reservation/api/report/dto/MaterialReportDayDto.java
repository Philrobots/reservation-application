package ca.ulaval.glo4002.reservation.api.report.dto;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "date", "cleaned", "bought", "totalPrice" })
public class MaterialReportDayDto {
  private final String date;
  private final BigDecimal totalPrice;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private final Map<String, BigDecimal> bought;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private final Map<String, BigDecimal> cleaned;

  public MaterialReportDayDto(String date, Map<String, BigDecimal> bought, Map<String, BigDecimal> cleaned, BigDecimal totalPrice) {
    this.date = date;
    this.bought = bought;
    this.cleaned = cleaned;
    this.totalPrice = totalPrice;
  }

  public String getDate() {
    return date;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public Map<String, BigDecimal> getBought() {
    return bought;
  }

  public Map<String, BigDecimal> getCleaned() {
    return cleaned;
  }

}
