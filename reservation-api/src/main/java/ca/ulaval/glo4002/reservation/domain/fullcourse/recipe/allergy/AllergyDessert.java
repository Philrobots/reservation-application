package ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.allergy;

import java.math.BigDecimal;

import ca.ulaval.glo4002.reservation.domain.fullcourse.Ingredient;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.fullcourse.Recipe;

public class AllergyDessert extends Recipe {
  private static final BigDecimal BACON_QUANTITY = BigDecimal.valueOf(5);

  public AllergyDessert() {
    ingredients.add(new Ingredient(IngredientName.BACON, BACON_QUANTITY));
  }
}
