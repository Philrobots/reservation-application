package ca.ulaval.glo4002.reservation.domain.fullcourse;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Collections;

import org.junit.jupiter.api.Test;

public class RecipeTest {

  private final Ingredient ingredient = new Ingredient(IngredientName.CARROTS,
                                                       BigDecimal.valueOf(3));

  private final Recipe recipe = new Recipe();

  @Test
  public void givenRecipeWithoutCarrots_whenContainsIngredient_thenReturnFalse() {
    // when
    boolean doContainCarrots = recipe.containsIngredient(IngredientName.CARROTS);

    // then
    assertFalse(doContainCarrots);
  }

  @Test
  public void givenRecipeWithCarrots_whenContainsIngredient_thenReturnTrue() {
    // given
    recipe.setIngredients(Collections.singletonList(ingredient));

    // when
    boolean doContainCarrots = recipe.containsIngredient(IngredientName.CARROTS);

    // then
    assertTrue(doContainCarrots);
  }
}
