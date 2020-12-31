package ca.ulaval.glo4002.reservation.domain.report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.infra.report.IngredientPriceDto;

public class IngredientReportGenerator {
  private final IngredientPriceCalculatorFactory ingredientPriceCalculatorFactory;
  private final IngredientReportFactory ingredientReportFactory;

  public IngredientReportGenerator(IngredientPriceCalculatorFactory ingredientPriceCalculatorFactory,
                                   IngredientReportFactory ingredientReportFactory)
  {
    this.ingredientPriceCalculatorFactory = ingredientPriceCalculatorFactory;
    this.ingredientReportFactory = ingredientReportFactory;
  }

  public IngredientReport generateReport(List<IngredientPriceDto> ingredientPriceDtos,
                                         Map<LocalDate, Map<IngredientName, BigDecimal>> ingredientQuantities)
  {
    IngredientPriceCalculator ingredientPriceCalculator = ingredientPriceCalculatorFactory.create(ingredientPriceDtos);
    return ingredientReportFactory.create(ingredientPriceCalculator, ingredientQuantities);
  }
}
