package ca.ulaval.glo4002.reservation.infra.report;

import java.math.BigDecimal;

public class IngredientPriceDto {
  private final String name;
  private final BigDecimal pricePerKg;

  public IngredientPriceDto(String name, BigDecimal pricePerKg) {
    this.name = name;
    this.pricePerKg = pricePerKg;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getPricePerKg() {
    return pricePerKg;
  }
}
