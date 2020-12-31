package ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.basic;

import java.math.BigDecimal;

import ca.ulaval.glo4002.reservation.domain.fullcourse.Ingredient;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.fullcourse.Recipe;

public class BasicStarter extends Recipe {
  private static final BigDecimal PORK_LOIN_QUANTITY = BigDecimal.valueOf(5);
  private static final BigDecimal CARROTS_QUANTITY = BigDecimal.valueOf(8);

  public BasicStarter() {
    ingredients.add(new Ingredient(IngredientName.PORK_LOIN, PORK_LOIN_QUANTITY));
    ingredients.add(new Ingredient(IngredientName.CARROTS, CARROTS_QUANTITY));
  }
}
