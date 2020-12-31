package ca.ulaval.glo4002.reservation.api.report.presenter;

import java.math.BigDecimal;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "name", "totalPrice", "quantity" })
public class IngredientReportInformationDto {
  @JsonProperty("name")
  private final String ingredientName;
  private final BigDecimal quantity;
  private final BigDecimal totalPrice;

  public IngredientReportInformationDto(String ingredientName,
                                        BigDecimal quantity,
                                        BigDecimal totalPrice)
  {
    this.ingredientName = ingredientName;
    this.quantity = quantity;
    this.totalPrice = totalPrice;
  }

  public String getIngredientName() {
    return ingredientName;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  @Override
  public boolean equals(Object o) {

    if (o == this)
      return true;
    if (!(o instanceof IngredientReportInformationDto)) {
      return false;
    }

    IngredientReportInformationDto ingredientReportInformationDto = (IngredientReportInformationDto) o;

    return ingredientReportInformationDto.ingredientName.equals(ingredientName)
           && ingredientReportInformationDto.quantity.compareTo(quantity) == 0
           && ingredientReportInformationDto.totalPrice.compareTo(totalPrice) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(ingredientName, quantity, totalPrice);
  }

}
