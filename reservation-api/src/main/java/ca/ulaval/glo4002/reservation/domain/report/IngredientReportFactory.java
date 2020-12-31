package ca.ulaval.glo4002.reservation.domain.report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;

public class IngredientReportFactory {

  private final DailyIngredientReportInformationFactory dailyIngredientReportInformationFactory;

  public IngredientReportFactory(DailyIngredientReportInformationFactory dailyIngredientReportInformationFactory) {
    this.dailyIngredientReportInformationFactory = dailyIngredientReportInformationFactory;
  }

  public IngredientReport create(IngredientPriceCalculator ingredientPriceCalculator,
                                 Map<LocalDate, Map<IngredientName, BigDecimal>> dailyIngredientsQuantities)
  {
    Map<LocalDate, DailyIngredientReportInformation> dailyIngredientsReportInformation = new HashMap<>();
    for (LocalDate date : dailyIngredientsQuantities.keySet()) {
      dailyIngredientsReportInformation.put(date,
                                            dailyIngredientReportInformationFactory.create(ingredientPriceCalculator,
                                                                                           dailyIngredientsQuantities.get(date)));
    }
    return new IngredientReport(dailyIngredientsReportInformation);
  }
}
