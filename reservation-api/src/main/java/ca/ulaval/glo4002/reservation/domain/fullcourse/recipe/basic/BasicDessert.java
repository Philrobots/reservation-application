package ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.basic;

import java.math.BigDecimal;

import ca.ulaval.glo4002.reservation.domain.fullcourse.Ingredient;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.fullcourse.Recipe;

public class BasicDessert extends Recipe {
  private static final BigDecimal ROAST_BEEF_QUANTITY = BigDecimal.valueOf(5);

  public BasicDessert() {
    ingredients.add(new Ingredient(IngredientName.ROAST_BEEF, ROAST_BEEF_QUANTITY));
  }
}