package ca.ulaval.glo4002.reservation.domain.report;

import java.math.BigDecimal;
import java.util.Set;

public class DailyIngredientReportInformation {
  private final Set<IngredientReportInformation> ingredientsReportInformation;

  public DailyIngredientReportInformation(Set<IngredientReportInformation> ingredientsReportInformation) {
    this.ingredientsReportInformation = ingredientsReportInformation;
  }

  public Set<IngredientReportInformation> getIngredientsReportInformation() {
    return ingredientsReportInformation;
  }

  public BigDecimal calculateDailyTotalPrice() {
    BigDecimal dailyTotalPrice = BigDecimal.valueOf(0);
    for (IngredientReportInformation ingredientReportInformation : ingredientsReportInformation) {
      dailyTotalPrice = dailyTotalPrice.add(ingredientReportInformation.getTotalPrice());
    }
    return dailyTotalPrice;
  }
}
