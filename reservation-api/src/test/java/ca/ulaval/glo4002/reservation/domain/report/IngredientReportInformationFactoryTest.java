package ca.ulaval.glo4002.reservation.domain.report;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;

@ExtendWith(MockitoExtension.class)
public class IngredientReportInformationFactoryTest {
  private static final BigDecimal PEPPERONI_QUANTITY = BigDecimal.valueOf(234.11);
  private static final BigDecimal PEPPERONI_TOTAL_PRICE = BigDecimal.valueOf(1636.4289);
  private static final BigDecimal KIWI_QUANTITY = BigDecimal.valueOf(24);
  private static final BigDecimal KIWI_TOTAL_PRICE = BigDecimal.valueOf(23.76);

  @Mock
  private IngredientPriceCalculator ingredientPriceCalculator;

  private IngredientReportInformationFactory ingredientReportInformationFactory;

  @BeforeEach
  public void setUp() {
    ingredientReportInformationFactory = new IngredientReportInformationFactory();
  }

  @Test
  public void givenIngredientsQuantities_whenCreate_thenTotalPriceIsCalculatedForEachIngredient() {
    // given
    Map<IngredientName, BigDecimal> someIngredientsQuantities = givenSomeIngredientsQuantities();

    // when
    ingredientReportInformationFactory.create(ingredientPriceCalculator, someIngredientsQuantities);

    // then
    verify(ingredientPriceCalculator).getTotalPrice(IngredientName.PEPPERONI, PEPPERONI_QUANTITY);
    verify(ingredientPriceCalculator).getTotalPrice(IngredientName.KIWI, KIWI_QUANTITY);
  }

  @Test
  public void givenIngredientsQuantities_whenCreate_thenIngredientReportInformationAreCreatedForEachIngredient() {
    // given
    given(ingredientPriceCalculator.getTotalPrice(IngredientName.PEPPERONI,
                                                  PEPPERONI_QUANTITY)).willReturn(PEPPERONI_TOTAL_PRICE);
    given(ingredientPriceCalculator.getTotalPrice(IngredientName.KIWI,
                                                  KIWI_QUANTITY)).willReturn(KIWI_TOTAL_PRICE);
    Map<IngredientName, BigDecimal> someIngredientsQuantities = givenSomeIngredientsQuantities();
    Set<IngredientReportInformation> expectedIngredientsReportInformation = expectedIngredientReportInformation();

    // when
    Set<IngredientReportInformation> ingredientsReportInformation = ingredientReportInformationFactory.create(ingredientPriceCalculator,
                                                                                                              someIngredientsQuantities);

    // then
    assertThat(ingredientsReportInformation).containsExactlyElementsIn(expectedIngredientsReportInformation);
  }

  private Map<IngredientName, BigDecimal> givenSomeIngredientsQuantities() {
    Map<IngredientName, BigDecimal> ingredientNameToQuantity = new HashMap<>();
    ingredientNameToQuantity.put(IngredientName.PEPPERONI, PEPPERONI_QUANTITY);
    ingredientNameToQuantity.put(IngredientName.KIWI, KIWI_QUANTITY);
    return ingredientNameToQuantity;
  }

  private Set<IngredientReportInformation> expectedIngredientReportInformation() {
    Set<IngredientReportInformation> expectedIngredientReportInformation = new HashSet<>();
    IngredientReportInformation pepperoniReportInformation = new IngredientReportInformation(IngredientName.PEPPERONI,
                                                                                             PEPPERONI_QUANTITY,
                                                                                             PEPPERONI_TOTAL_PRICE);
    IngredientReportInformation kiwiReportInformation = new IngredientReportInformation(IngredientName.KIWI,
                                                                                        KIWI_QUANTITY,
                                                                                        KIWI_TOTAL_PRICE);
    expectedIngredientReportInformation.add(pepperoniReportInformation);
    expectedIngredientReportInformation.add(kiwiReportInformation);
    return expectedIngredientReportInformation;
  }
}
