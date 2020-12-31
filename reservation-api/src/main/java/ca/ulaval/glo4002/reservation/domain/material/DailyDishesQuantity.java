package ca.ulaval.glo4002.reservation.domain.material;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class DailyDishesQuantity {
  private static final BigDecimal BASE_NUMBER_OF_DISHES = BigDecimal.valueOf(3);
  private static final BigDecimal SUPPLEMENTARY_DISHES_COUNT_FOR_RESTRICTION = BigDecimal.valueOf(1);

  private final Map<Material, BigDecimal> dishesQuantity = new HashMap<>();

  public DailyDishesQuantity() {
  }

  public DailyDishesQuantity(int numberOfCustomers, int numberOfRestrictions) {
    updateQuantity(numberOfCustomers, numberOfRestrictions);
  }

  public void updateQuantity(int numberOfCustomers, int numberOfRestrictions) {
    if (dishesQuantity.isEmpty()) {
      addBaseDishesCount(numberOfCustomers, numberOfRestrictions);
    } else {
      updateExistingDishesCount(numberOfCustomers, numberOfRestrictions);
    }
  }

  public Map<Material, BigDecimal> getDishesQuantity() {
    return dishesQuantity;
  }

  private void addBaseDishesCount(int numberOfCustomers, int numberOfRestrictions) {
    final BigDecimal totalNumberOfDishesNeeded = BASE_NUMBER_OF_DISHES.multiply(BigDecimal.valueOf(numberOfCustomers));
    BigDecimal totalNumberOfDishesNeededForRestrictions = totalNumberOfDishesNeeded.add(SUPPLEMENTARY_DISHES_COUNT_FOR_RESTRICTION.multiply(BigDecimal.valueOf(numberOfRestrictions)));
    dishesQuantity.put(Material.FORK, totalNumberOfDishesNeededForRestrictions);
    dishesQuantity.put(Material.SPOON, totalNumberOfDishesNeeded);
    dishesQuantity.put(Material.KNIFE, totalNumberOfDishesNeeded);
    dishesQuantity.put(Material.PLATE, totalNumberOfDishesNeededForRestrictions);
    dishesQuantity.put(Material.BOWL, totalNumberOfDishesNeeded);
  }

  private void updateExistingDishesCount(int numberOfCustomers, int numberOfRestrictions) {
    final BigDecimal quantityToAdd = BASE_NUMBER_OF_DISHES.multiply(BigDecimal.valueOf(numberOfCustomers));
    dishesQuantity.replaceAll((material, quantity) -> dishesQuantity.get(material)
                                                                    .add(quantityToAdd));
    if (numberOfCustomers > 0) {
      updateExistingDishesCountForRestriction(numberOfRestrictions);
    }
  }

  private void updateExistingDishesCountForRestriction(int numberOfRestrictions) {
    BigDecimal numberOfPlatesAndForksToAdd = BigDecimal.valueOf(numberOfRestrictions);
    dishesQuantity.replace(Material.FORK,
                           dishesQuantity.get(Material.FORK).add(numberOfPlatesAndForksToAdd));
    dishesQuantity.replace(Material.PLATE,
                           dishesQuantity.get(Material.PLATE).add(numberOfPlatesAndForksToAdd));
  }
}
