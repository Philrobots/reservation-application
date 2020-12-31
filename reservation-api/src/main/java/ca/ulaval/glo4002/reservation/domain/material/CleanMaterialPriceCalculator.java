package ca.ulaval.glo4002.reservation.domain.material;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class CleanMaterialPriceCalculator {
  private static final BigDecimal CLEANING_PRICE_FOR_CLEANER_CAPACITY = BigDecimal.valueOf(100);
  private static final BigDecimal CLEANER_CAPACITY = BigDecimal.valueOf(9);

  public BigDecimal calculateCleaningPrice(Map<Material, BigDecimal> cleanedDishes) {
    BigDecimal amountCleanDishes = BigDecimal.ZERO;
    for (Map.Entry<Material, BigDecimal> materialEntry : cleanedDishes.entrySet()) {
      amountCleanDishes = amountCleanDishes.add(materialEntry.getValue());
    }
    return CLEANING_PRICE_FOR_CLEANER_CAPACITY.multiply(amountCleanDishes.divide(CLEANER_CAPACITY,
                                                                                 RoundingMode.CEILING));
  }
}
