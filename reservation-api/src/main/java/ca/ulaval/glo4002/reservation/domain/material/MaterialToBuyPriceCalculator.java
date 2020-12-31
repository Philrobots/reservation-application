package ca.ulaval.glo4002.reservation.domain.material;

import java.math.BigDecimal;
import java.util.Map;

public class MaterialToBuyPriceCalculator {
  public BigDecimal calculateBuyPrice(Map<Material, BigDecimal> boughtDishes) {
    BigDecimal boughtPrice = BigDecimal.ZERO;
    for (Map.Entry<Material, BigDecimal> materialEntry : boughtDishes.entrySet()) {
      boughtPrice = boughtPrice.add(materialEntry.getKey()
                                                 .getPrice()
                                                 .multiply(materialEntry.getValue()));
    }
    return boughtPrice;
  }
}
