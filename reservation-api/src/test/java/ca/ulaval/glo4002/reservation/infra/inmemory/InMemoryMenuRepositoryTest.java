package ca.ulaval.glo4002.reservation.infra.inmemory;

import static com.google.common.truth.Truth.assertThat;

import java.math.BigDecimal;
import java.util.Map;

import ca.ulaval.glo4002.reservation.domain.fullcourse.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.reservation.RestrictionType;

public class InMemoryMenuRepositoryTest {
  private static final RestrictionType ALLERGIES_RESTRICTION = RestrictionType.ALLERGIES;

  private final FullCourseFactory fullCourseFactory = new FullCourseFactory(new CourseRecipeFactory());

  private MenuRepository menuRepository;

  @BeforeEach
  public void setUp() {
    menuRepository = new InMemoryMenuRepository(fullCourseFactory);
  }

  @Test
  public void whenGetIngredientsQuantity_thenReturnReturnCorrespondingIngredientInformation() {
    // when
    Map<IngredientName, BigDecimal> ingredientsQuantity = menuRepository.getIngredientsQuantityByRestrictionType(ALLERGIES_RESTRICTION);

    // then
    assertThat(ingredientsQuantity).isNotEmpty();
  }
}
