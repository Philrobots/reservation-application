package ca.ulaval.glo4002.reservation.domain.fullcourse;

import java.math.BigDecimal;
import java.util.Map;

import ca.ulaval.glo4002.reservation.domain.reservation.RestrictionType;

public interface MenuRepository {
  Map<IngredientName, BigDecimal> getIngredientsQuantityByRestrictionType(RestrictionType restrictionType);
}
