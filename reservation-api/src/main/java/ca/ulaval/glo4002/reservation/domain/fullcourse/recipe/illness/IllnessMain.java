package ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.illness;

import java.math.BigDecimal;

import ca.ulaval.glo4002.reservation.domain.fullcourse.Ingredient;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.fullcourse.Recipe;

public class IllnessMain extends Recipe {
  private static final BigDecimal KIWI_QUANTITY = BigDecimal.valueOf(5);

  public IllnessMain() {
    ingredients.add(new Ingredient(IngredientName.KIWI, KIWI_QUANTITY));
  }
}
