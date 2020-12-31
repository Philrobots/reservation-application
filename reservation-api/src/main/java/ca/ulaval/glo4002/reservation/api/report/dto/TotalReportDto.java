package ca.ulaval.glo4002.reservation.api.report.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ca.ulaval.glo4002.reservation.api.report.presenter.IngredientReportInformationDto;

@JsonPropertyOrder({ "ingredients", "totalPrice" })
public class TotalReportDto {
  @JsonProperty("ingredients")
  private final List<IngredientReportInformationDto> ingredients;
  private final BigDecimal totalPrice;

  public TotalReportDto(List<IngredientReportInformationDto> ingredients, BigDecimal totalPrice) {
    this.ingredients = ingredients;
    this.totalPrice = totalPrice;
  }

  public List<IngredientReportInformationDto> getIngredients() {
    return ingredients;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }
}
