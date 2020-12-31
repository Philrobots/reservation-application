package ca.ulaval.glo4002.reservation.domain.reservation;

import java.math.BigDecimal;

public enum RestrictionType {
  VEGETARIAN("vegetarian", BigDecimal.valueOf(500)),
  VEGAN("vegan", BigDecimal.valueOf(1000)),
  ALLERGIES("allergies", BigDecimal.ZERO),
  ILLNESS("illness", BigDecimal.ZERO),
  NONE("none", BigDecimal.ZERO);

  private final String name;

  private final BigDecimal fees;

  RestrictionType(String name, BigDecimal fees) {
    this.name = name;
    this.fees = fees;
  }

  public static RestrictionType valueOfName(String value) {
    for (RestrictionType restriction : RestrictionType.values()) {
      if (restriction.toString().equals(value)) {
        return restriction;
      }
    }
    throw new InvalidRestrictionException();
  }

  @Override
  public String toString() {
    return name;
  }

  public BigDecimal getFees() {
    return fees;
  }
}
