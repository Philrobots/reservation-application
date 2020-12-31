package ca.ulaval.glo4002.reservation.domain.report;

import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;

@ExtendWith(MockitoExtension.class)
public class DailyIngredientReportInformationFactoryTest {
  private static final BigDecimal PEPPERONI_QUANTITY = BigDecimal.valueOf(234.11);
  private static final BigDecimal KIWI_QUANTITY = BigDecimal.valueOf(24);

  @Mock
  private IngredientReportInformationFactory ingredientReportInformationFactory;

  @Mock
  private IngredientPriceCalculator ingredientPriceCalculator;

  private DailyIngredientReportInformationFactory dailyIngredientReportInformationFactory;

  @BeforeEach
  public void setUp() {
    dailyIngredientReportInformationFactory = new DailyIngredientReportInformationFactory(ingredientReportInformationFactory);
  }

  @Test
  public void givenDailyIngredientsQuantities_whenCreate_thenIngredientReportInformationAreCreatedForEachDay() {
    // given
    Map<IngredientName, BigDecimal> someIngredientsQuantities = givenSomeIngredientsQuantities();

    // when
    dailyIngredientReportInformationFactory.create(ingredientPriceCalculator,
                                                   someIngredientsQuantities);

    // then
    verify(ingredientReportInformationFactory).create(ingredientPriceCalculator,
                                                      someIngredientsQuantities);

  }

  private Map<IngredientName, BigDecimal> givenSomeIngredientsQuantities() {
    Map<IngredientName, BigDecimal> ingredientNameToQuantity = new HashMap<>();
    ingredientNameToQuantity.put(IngredientName.PEPPERONI, PEPPERONI_QUANTITY);
    ingredientNameToQuantity.put(IngredientName.KIWI, KIWI_QUANTITY);
    return ingredientNameToQuantity;
  }
}
