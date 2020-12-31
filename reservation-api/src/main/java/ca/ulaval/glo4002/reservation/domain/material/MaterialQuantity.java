package ca.ulaval.glo4002.reservation.domain.material;

import java.math.BigDecimal;

public class MaterialQuantity {

  private final Material material;
  private BigDecimal quantity;

  public MaterialQuantity(Material material, BigDecimal quantity) {
    this.material = material;
    this.quantity = quantity;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public Material getMaterial() {
    return material;
  }

  public void addQuantity(BigDecimal quantityToAdd) {
    quantity = quantity.add(quantityToAdd);
  }
}
