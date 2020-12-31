package ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.vegan;

import java.math.BigDecimal;

import ca.ulaval.glo4002.reservation.domain.fullcourse.Ingredient;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.fullcourse.Recipe;

public class VeganMain extends Recipe {
  private static final BigDecimal KIMCHI_QUANTITY = BigDecimal.valueOf(10);

  public VeganMain() {
    ingredients.add(new Ingredient(IngredientName.KIMCHI, KIMCHI_QUANTITY));
  }
}
