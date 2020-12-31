package ca.ulaval.glo4002.reservation.domain.fullcourse;

import ca.ulaval.glo4002.reservation.domain.exception.IngredientNameDoesNotExistException;

public enum IngredientName {
  PORK_LOIN("Pork loin"),
  CARROTS("Carrots"),
  PEPPERONI("Pepperoni"),
  ROAST_BEEF("Roast beef"),
  WATER("Water"),
  PUMPKIN("Pumpkin"),
  CHOCOLATE("Chocolate"),
  TUNA("Tuna"),
  MOZZARELLA("Mozzarella"),
  TOMATO("Tomato"),
  KIWI("Kiwi"),
  KIMCHI("Kimchi"),
  WORCESTERSHIRE_SAUCE("Worcestershire sauce"),
  MARMALADE("Marmalade"),
  PLANTAIN("Plantain"),
  TOFU("Tofu"),
  BACON("Bacon"),
  SCALLOPS("Scallops"),
  BUTTERNUT_SQUASH("Butternut squash");

  private final String name;

  IngredientName(String name) {
    this.name = name;
  }

  public static IngredientName valueOfName(String name) {
    for (IngredientName ingredientName : IngredientName.values()) {
      if (ingredientName.toString().equals(name)) {
        return ingredientName;
      }
    }
    throw new IngredientNameDoesNotExistException();
  }

  public static boolean contains(String name) {
    for (IngredientName ingredientName : IngredientName.values()) {
      if (ingredientName.toString().equals(name)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return name;
  }
}
