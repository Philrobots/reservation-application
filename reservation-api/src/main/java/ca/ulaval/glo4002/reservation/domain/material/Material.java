package ca.ulaval.glo4002.reservation.domain.material;

import java.math.BigDecimal;

public enum Material {
  BOWL("bowl", BigDecimal.valueOf(170)),
  PLATE("plate", BigDecimal.valueOf(170)),
  KNIFE("knife", BigDecimal.valueOf(20)),
  FORK("fork", BigDecimal.valueOf(20)),
  SPOON("spoon", BigDecimal.valueOf(20));

  private final String name;
  private final BigDecimal price;

  Material(String name, BigDecimal price) {
    this.name = name;
    this.price = price;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public String getName() {
    return name;
  }
}
