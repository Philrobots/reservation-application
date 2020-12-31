package ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.vegetarian;

import java.math.BigDecimal;

import ca.ulaval.glo4002.reservation.domain.fullcourse.Ingredient;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.fullcourse.Recipe;

public class VegetarianDessert extends Recipe {
  private static final BigDecimal MOZZARELLA_QUANTITY = BigDecimal.valueOf(5);

  public VegetarianDessert() {
    ingredients.add(new Ingredient(IngredientName.MOZZARELLA, MOZZARELLA_QUANTITY));
  }
}
