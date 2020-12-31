package ca.ulaval.glo4002.reservation.domain.report;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;

public class DailyIngredientReportInformationFactory {
  private final IngredientReportInformationFactory ingredientReportInformationFactory;

  public DailyIngredientReportInformationFactory(IngredientReportInformationFactory ingredientReportInformationFactory) {
    this.ingredientReportInformationFactory = ingredientReportInformationFactory;
  }

  public DailyIngredientReportInformation create(IngredientPriceCalculator ingredientPriceCalculator,
                                                 Map<IngredientName, BigDecimal> ingredientsQuantities)
  {
    Set<IngredientReportInformation> ingredientsReportInformation = ingredientReportInformationFactory.create(ingredientPriceCalculator,
                                                                                                              ingredientsQuantities);
    return new DailyIngredientReportInformation(ingredientsReportInformation);
  }
}
