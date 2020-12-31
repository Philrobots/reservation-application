package ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.vegan;

import java.math.BigDecimal;

import ca.ulaval.glo4002.reservation.domain.fullcourse.Ingredient;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.fullcourse.Recipe;

public class VeganStarter extends Recipe {
  private static final BigDecimal TOMATO_QUANTITY = BigDecimal.valueOf(5);
  private static final BigDecimal KIWI_QUANTITY = BigDecimal.valueOf(8);

  public VeganStarter() {
    ingredients.add(new Ingredient(IngredientName.TOMATO, TOMATO_QUANTITY));
    ingredients.add(new Ingredient(IngredientName.KIWI, KIWI_QUANTITY));
  }
}
