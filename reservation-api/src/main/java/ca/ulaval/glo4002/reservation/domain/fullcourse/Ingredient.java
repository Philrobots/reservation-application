package ca.ulaval.glo4002.reservation.domain.fullcourse;

import java.math.BigDecimal;

public class Ingredient {
  private final IngredientName ingredientName;
  private final BigDecimal quantity;

  public Ingredient(IngredientName ingredientName, BigDecimal quantity) {
    this.ingredientName = ingredientName;
    this.quantity = quantity;
  }

  public IngredientName getIngredientName() {
    return ingredientName;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }
}
