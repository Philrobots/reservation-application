package ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.allergy;

import java.math.BigDecimal;

import ca.ulaval.glo4002.reservation.domain.fullcourse.Ingredient;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.fullcourse.Recipe;

public class AllergyStarter extends Recipe {
  private static final BigDecimal MARMALADE_QUANTITY = BigDecimal.valueOf(5);
  private static final BigDecimal PLANTAIN_QUANTITY = BigDecimal.valueOf(8);

  public AllergyStarter() {
    ingredients.add(new Ingredient(IngredientName.MARMALADE, MARMALADE_QUANTITY));
    ingredients.add(new Ingredient(IngredientName.PLANTAIN, PLANTAIN_QUANTITY));
  }
}
