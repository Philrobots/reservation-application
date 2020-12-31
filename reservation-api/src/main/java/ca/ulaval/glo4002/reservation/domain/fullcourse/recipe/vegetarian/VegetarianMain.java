package ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.vegetarian;

import java.math.BigDecimal;

import ca.ulaval.glo4002.reservation.domain.fullcourse.Ingredient;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.fullcourse.Recipe;

public class VegetarianMain extends Recipe {
  private static final BigDecimal TUNA_QUANTITY = BigDecimal.valueOf(10);

  public VegetarianMain() {
    ingredients.add(new Ingredient(IngredientName.TUNA, TUNA_QUANTITY));
  }
}
