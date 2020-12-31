package ca.ulaval.glo4002.reservation.domain.fullcourse;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
  protected List<Ingredient> ingredients;

  public Recipe() {
    ingredients = new ArrayList<>();
  }

  public List<Ingredient> getIngredients() {
    return ingredients;
  }

  protected void setIngredients(List<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }

  public boolean containsIngredient(IngredientName ingredientName) {
    return ingredients.stream().anyMatch(ingredient -> ingredient.getIngredientName() == ingredientName);
  }
}
