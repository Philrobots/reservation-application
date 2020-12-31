package ca.ulaval.glo4002.reservation.domain.fullcourse.stock;

import java.time.LocalDate;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;

public class TomatoStock implements Available {

  private final IngredientName ingredient;

  private final int ingredientPreparationDays;

  public TomatoStock(IngredientName ingredient, int ingredientPreparationDays) {
    this.ingredient = ingredient;
    this.ingredientPreparationDays = ingredientPreparationDays;
  }

  public boolean isAvailable(LocalDate dinnerDate, LocalDate openingDate) {
    LocalDate tomatoAvailabilityDate = openingDate.plusDays(ingredientPreparationDays);
    return tomatoAvailabilityDate.isBefore(dinnerDate)
           || tomatoAvailabilityDate.isEqual(dinnerDate);
  }

  public IngredientName getIngredientName() {
    return ingredient;
  }
}
