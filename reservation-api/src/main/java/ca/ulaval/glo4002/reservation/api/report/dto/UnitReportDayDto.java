package ca.ulaval.glo4002.reservation.api.report.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import ca.ulaval.glo4002.reservation.api.report.presenter.IngredientReportInformationDto;

public class UnitReportDayDto {
  private String date;
  private List<IngredientReportInformationDto> ingredients;
  private BigDecimal totalPrice;

  public UnitReportDayDto(LocalDate date, List<IngredientReportInformationDto> ingredients, BigDecimal totalPrice) {
    this.date = date.toString();
    this.ingredients = ingredients;
    this.totalPrice = totalPrice;
  }

  public String getDate() {
    return date;
  }

  public List<IngredientReportInformationDto> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<IngredientReportInformationDto> ingredients) {
    this.ingredients = ingredients;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  @Override
  public boolean equals(Object o) {

    if (o == this)
      return true;
    if (!(o instanceof UnitReportDayDto)) {
      return false;
    }

    UnitReportDayDto unitReportDayDto = (UnitReportDayDto) o;

    return unitReportDayDto.date.equals(date) && unitReportDayDto.ingredients.equals(ingredients)
           && unitReportDayDto.totalPrice.compareTo(totalPrice) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, ingredients, totalPrice);
  }
}
