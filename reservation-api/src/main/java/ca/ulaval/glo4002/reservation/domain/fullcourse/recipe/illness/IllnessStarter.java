package ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.illness;

import java.math.BigDecimal;

import ca.ulaval.glo4002.reservation.domain.fullcourse.Ingredient;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.fullcourse.Recipe;

public class IllnessStarter extends Recipe {
  private static final BigDecimal SCALLOPS_QUANTITY = BigDecimal.valueOf(2);
  private static final BigDecimal BUTTERNUT_SQUASH_QUANTITY = BigDecimal.valueOf(4);

  public IllnessStarter() {
    ingredients.add(new Ingredient(IngredientName.SCALLOPS, SCALLOPS_QUANTITY));
    ingredients.add(new Ingredient(IngredientName.BUTTERNUT_SQUASH, BUTTERNUT_SQUASH_QUANTITY));
  }
}
