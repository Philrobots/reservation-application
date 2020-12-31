package ca.ulaval.glo4002.reservation.domain.fullcourse;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FullCourse {
  private final List<Recipe> recipes;

  public FullCourse(List<Recipe> recipes) {
    this.recipes = recipes;
  }

  public Map<IngredientName, BigDecimal> getIngredientQuantities() {
    Map<IngredientName, BigDecimal> ingredientsQuantity = new HashMap<>();
    for (Recipe recipe : recipes) {
      ingredientsQuantity = updateIngredientsQuantity(ingredientsQuantity, recipe.getIngredients());
    }
    return ingredientsQuantity;
  }

  private Map<IngredientName, BigDecimal> updateIngredientsQuantity(Map<IngredientName, BigDecimal> ingredientsQuantity,
                                                                    List<Ingredient> ingredients)
  {
    Map<IngredientName, BigDecimal> updatedTotalIngredientsQuantity = new HashMap<>(ingredientsQuantity);
    for (Ingredient ingredient : ingredients) {
      if (updatedTotalIngredientsQuantity.containsKey(ingredient.getIngredientName())) {
        updatedTotalIngredientsQuantity.replace(ingredient.getIngredientName(),
                                                updatedTotalIngredientsQuantity.get(ingredient.getIngredientName())
                                                                               .add(ingredient.getQuantity()));
      } else {
        updatedTotalIngredientsQuantity.put(ingredient.getIngredientName(),
                                            ingredient.getQuantity());
      }
    }
    return updatedTotalIngredientsQuantity;
  }

}
