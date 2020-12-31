package ca.ulaval.glo4002.reservation.domain.report;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.report.exception.IngredientNotFoundException;
import ca.ulaval.glo4002.reservation.infra.report.IngredientPriceDto;

public class IngredientPriceCalculator {
  private final Map<IngredientName, BigDecimal> ingredientNameToPrice;

  public IngredientPriceCalculator() {
    this.ingredientNameToPrice = new HashMap<>();
  }

  public IngredientPriceCalculator(Map<IngredientName, BigDecimal> ingredientNameToPrice) {
    this.ingredientNameToPrice = ingredientNameToPrice;
  }

  public void generatePriceMapper(List<IngredientPriceDto> ingredientPriceDtos) {
    for (IngredientPriceDto ingredientPriceDto : ingredientPriceDtos) {
      if (IngredientName.contains(ingredientPriceDto.getName())) {
        ingredientNameToPrice.put(IngredientName.valueOfName(ingredientPriceDto.getName()),
                                  ingredientPriceDto.getPricePerKg());
      }
    }
  }

  public BigDecimal getTotalPrice(IngredientName ingredientName, BigDecimal quantity) {
    try {
      return ingredientNameToPrice.get(ingredientName).multiply(quantity);
    } catch (NullPointerException e) {
      throw new IngredientNotFoundException();
    }
  }

  public Map<IngredientName, BigDecimal> getIngredientNameToPrice() {
    return ingredientNameToPrice;
  }
}
