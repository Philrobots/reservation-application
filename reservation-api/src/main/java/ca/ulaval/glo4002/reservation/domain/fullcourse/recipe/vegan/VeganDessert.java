package ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.vegan;

import java.math.BigDecimal;

import ca.ulaval.glo4002.reservation.domain.fullcourse.Ingredient;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.fullcourse.Recipe;

public class VeganDessert extends Recipe {
  private static final BigDecimal WORCESTERSHIRE_SAUCE_QUANTITY = BigDecimal.valueOf(5);

  public VeganDessert() {
    ingredients.add(new Ingredient(IngredientName.WORCESTERSHIRE_SAUCE,
                                   WORCESTERSHIRE_SAUCE_QUANTITY));
  }
}
