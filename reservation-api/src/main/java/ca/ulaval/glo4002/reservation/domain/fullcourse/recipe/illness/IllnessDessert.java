package ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.illness;

import java.math.BigDecimal;

import ca.ulaval.glo4002.reservation.domain.fullcourse.Ingredient;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.fullcourse.Recipe;

public class IllnessDessert extends Recipe {
  private static final BigDecimal PEPPERONI_QUANTITY = BigDecimal.valueOf(2);

  public IllnessDessert() {
    ingredients.add(new Ingredient(IngredientName.PEPPERONI, PEPPERONI_QUANTITY));
  }
}
