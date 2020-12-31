package ca.ulaval.glo4002.reservation.infra.inmemory;

import java.math.BigDecimal;
import java.util.Map;

import ca.ulaval.glo4002.reservation.domain.fullcourse.FullCourse;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.fullcourse.MenuRepository;
import ca.ulaval.glo4002.reservation.domain.reservation.RestrictionType;

public class InMemoryMenuRepository implements MenuRepository {
  private final Map<RestrictionType, FullCourse> menu;

  public InMemoryMenuRepository(FullCourseFactory fullCourseFactory) {
    menu = fullCourseFactory.create();
  }

  public Map<IngredientName, BigDecimal> getIngredientsQuantityByRestrictionType(RestrictionType restrictionType) {
    return getCourseByRestrictionType(restrictionType).getIngredientQuantities();
  }

  private FullCourse getCourseByRestrictionType(RestrictionType restrictionType) {
    return menu.get(restrictionType);
  }
}
