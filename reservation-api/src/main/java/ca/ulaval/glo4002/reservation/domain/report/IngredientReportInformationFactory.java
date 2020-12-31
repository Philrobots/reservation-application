package ca.ulaval.glo4002.reservation.domain.report;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;

public class IngredientReportInformationFactory {

  public Set<IngredientReportInformation> create(IngredientPriceCalculator ingredientPriceCalculator,
                                                 Map<IngredientName, BigDecimal> ingredientsQuantities)
  {
    Set<IngredientReportInformation> ingredientsReportInformation = new HashSet<>();
    for (Map.Entry<IngredientName, BigDecimal> entry : ingredientsQuantities.entrySet()) {
      IngredientName ingredientName = entry.getKey();
      BigDecimal quantity = entry.getValue();
      BigDecimal totalPrice = ingredientPriceCalculator.getTotalPrice(entry.getKey(),
                                                                      entry.getValue());
      IngredientReportInformation ingredientReportInformation = new IngredientReportInformation(ingredientName,
                                                                                                quantity,
                                                                                                totalPrice);
      ingredientsReportInformation.add(ingredientReportInformation);
    }
    return ingredientsReportInformation;
  }
}
