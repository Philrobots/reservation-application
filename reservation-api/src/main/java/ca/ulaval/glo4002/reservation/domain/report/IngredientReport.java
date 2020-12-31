package ca.ulaval.glo4002.reservation.domain.report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;

public class IngredientReport {
  private final Map<LocalDate, DailyIngredientReportInformation> dailyIngredientsInformation;

  public IngredientReport(Map<LocalDate, DailyIngredientReportInformation> dailyIngredientReportInformation) {
    this.dailyIngredientsInformation = dailyIngredientReportInformation;
  }

  public BigDecimal calculateTotalPriceForEntireReport() {
    BigDecimal totalPrice = BigDecimal.valueOf(0);
    for (DailyIngredientReportInformation ingredientsReportInformation : dailyIngredientsInformation.values()) {
      totalPrice = totalPrice.add(ingredientsReportInformation.calculateDailyTotalPrice());
    }
    return totalPrice;
  }

  public Map<LocalDate, DailyIngredientReportInformation> getDailyIngredientsInformation() {
    return dailyIngredientsInformation;
  }

  public Map<IngredientName, IngredientReportInformation> generateTotalIngredientReportInformation() {
    Map<IngredientName, IngredientReportInformation> ingredientNameToIngredientReportInformation = new HashMap<>();
    for (DailyIngredientReportInformation dailyIngredientReportInformation : dailyIngredientsInformation.values()) {
      for (IngredientReportInformation ingredientReportInformationToAdd : dailyIngredientReportInformation.getIngredientsReportInformation()) {
        updateIngredientInformation(ingredientNameToIngredientReportInformation, ingredientReportInformationToAdd);
      }
    }
    return ingredientNameToIngredientReportInformation;
  }

  private void updateIngredientInformation(Map<IngredientName, IngredientReportInformation> ingredientsInformation,
                                           IngredientReportInformation ingredientReportInformationToAdd)
  {
    IngredientName ingredientName = ingredientReportInformationToAdd.getIngredientName();
    if (ingredientsInformation.containsKey(ingredientName)) {
      updateExistingIngredientInformation(ingredientsInformation, ingredientReportInformationToAdd);
    } else {
      ingredientsInformation.put(ingredientName,
                                 new IngredientReportInformation(ingredientName,
                                                                 ingredientReportInformationToAdd.getQuantity(),
                                                                 ingredientReportInformationToAdd.getTotalPrice()));
    }
  }

  private void updateExistingIngredientInformation(Map<IngredientName, IngredientReportInformation> ingredientsInformation,
                                                   IngredientReportInformation ingredientReportInformationToAdd)
  {
    BigDecimal updatedQuantity = ingredientsInformation.get(ingredientReportInformationToAdd.getIngredientName())
                                                       .getQuantity()
                                                       .add(ingredientReportInformationToAdd.getQuantity());
    BigDecimal updatedPrice = ingredientsInformation.get(ingredientReportInformationToAdd.getIngredientName())
                                                    .getTotalPrice()
                                                    .add(ingredientReportInformationToAdd.getTotalPrice());

    ingredientsInformation.put(ingredientReportInformationToAdd.getIngredientName(),
                               new IngredientReportInformation(ingredientReportInformationToAdd.getIngredientName(),
                                                               updatedQuantity,
                                                               updatedPrice));
  }
}
