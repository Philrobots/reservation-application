package ca.ulaval.glo4002.reservation.domain.report;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.infra.report.IngredientPriceDto;

public class IngredientPriceCalculatorFactory {
  public IngredientPriceCalculator create(List<IngredientPriceDto> ingredientPriceDtos) {
    Map<IngredientName, BigDecimal> ingredientNameToPrice = createIngredientNameToPrice(ingredientPriceDtos);
    return new IngredientPriceCalculator(ingredientNameToPrice);
  }

  private Map<IngredientName, BigDecimal> createIngredientNameToPrice(List<IngredientPriceDto> ingredientPriceDtos) {
    Map<IngredientName, BigDecimal> ingredientNameToPrice = new HashMap<>();
    for (IngredientPriceDto ingredientPriceDto : ingredientPriceDtos) {
      if (IngredientName.contains(ingredientPriceDto.getName())) {
        ingredientNameToPrice.put(IngredientName.valueOfName(ingredientPriceDto.getName()),
                                  ingredientPriceDto.getPricePerKg());
      }
    }
    return ingredientNameToPrice;
  }
}
