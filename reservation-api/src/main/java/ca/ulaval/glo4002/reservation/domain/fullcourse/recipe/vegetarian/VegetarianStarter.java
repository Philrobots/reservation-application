package ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.vegetarian;

import java.math.BigDecimal;

import ca.ulaval.glo4002.reservation.domain.fullcourse.Ingredient;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.fullcourse.Recipe;

public class VegetarianStarter extends Recipe {
  private static final BigDecimal PUMPKIN_QUANTITY = BigDecimal.valueOf(5);
  private static final BigDecimal CHOCOLATE_QUANTITY = BigDecimal.valueOf(8);

  public VegetarianStarter() {
    ingredients.add(new Ingredient(IngredientName.PUMPKIN, PUMPKIN_QUANTITY));
    ingredients.add(new Ingredient(IngredientName.CHOCOLATE, CHOCOLATE_QUANTITY));
  }
}
