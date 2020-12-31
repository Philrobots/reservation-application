package ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.basic;

import java.math.BigDecimal;

import ca.ulaval.glo4002.reservation.domain.fullcourse.Ingredient;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.fullcourse.Recipe;

public class BasicMain extends Recipe {
  private static final BigDecimal PEPPERONI_QUANTITY = BigDecimal.TEN;

  public BasicMain() {
    ingredients.add(new Ingredient(IngredientName.PEPPERONI, PEPPERONI_QUANTITY));
  }
}