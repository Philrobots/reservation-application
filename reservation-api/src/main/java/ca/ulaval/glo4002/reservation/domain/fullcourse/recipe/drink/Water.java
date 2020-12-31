package ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.drink;

import java.math.BigDecimal;

import ca.ulaval.glo4002.reservation.domain.fullcourse.Ingredient;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.fullcourse.Recipe;

public class Water extends Recipe {
  private static final BigDecimal WATER_QUANTITY = BigDecimal.valueOf(0.1);

  public Water() {
    ingredients.add(new Ingredient(IngredientName.WATER, WATER_QUANTITY));
  }
}