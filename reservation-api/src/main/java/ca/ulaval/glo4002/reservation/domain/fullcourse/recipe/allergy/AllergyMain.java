package ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.allergy;

import java.math.BigDecimal;

import ca.ulaval.glo4002.reservation.domain.fullcourse.Ingredient;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.fullcourse.Recipe;

public class AllergyMain extends Recipe {
  private static final BigDecimal TOFU_QUANTITY = BigDecimal.valueOf(10);

  public AllergyMain() {
    ingredients.add(new Ingredient(IngredientName.TOFU, TOFU_QUANTITY));
  }
}
