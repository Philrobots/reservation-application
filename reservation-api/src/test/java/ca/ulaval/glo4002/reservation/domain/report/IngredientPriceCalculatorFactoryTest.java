package ca.ulaval.glo4002.reservation.domain.report;

import static com.google.common.truth.Truth.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.infra.report.IngredientPriceDto;

public class IngredientPriceCalculatorFactoryTest {
  private static final String AN_INGREDIENT_NAME_STRINGIFY = IngredientName.BUTTERNUT_SQUASH.toString();
  private static final String ANOTHER_INGREDIENT_NAME_STRINGIFY = IngredientName.CHOCOLATE.toString();
  private static final IngredientName AN_INGREDIENT_NAME = IngredientName.BUTTERNUT_SQUASH;
  private static final IngredientName ANOTHER_INGREDIENT_NAME = IngredientName.CHOCOLATE;
  private static final BigDecimal A_PRICE = BigDecimal.valueOf(10.42);
  private static final BigDecimal ANOTHER_PRICE = BigDecimal.valueOf(5.93);

  private IngredientPriceCalculatorFactory ingredientPriceCalculatorFactory;

  @BeforeEach
  public void setUpIngredientPriceCalculatorFactory() {
    ingredientPriceCalculatorFactory = new IngredientPriceCalculatorFactory();
  }

  @Test
  public void givenIngredientPriceDtos_whenCreate_thenIngredientPriceCalculatorIsCreatedWithIngredientNameToPrice() {
    // given
    List<IngredientPriceDto> givenIngredientPriceDtos = givenIngredientPriceDtos();
    Map<IngredientName, BigDecimal> expectedIngredientNameToPrice = givenExpectedIngredientNameToPrice();

    // when
    IngredientPriceCalculator ingredientPriceCalculator = ingredientPriceCalculatorFactory.create(givenIngredientPriceDtos);

    // then
    assertThat(ingredientPriceCalculator.getIngredientNameToPrice()).isEqualTo(expectedIngredientNameToPrice);

  }

  private List<IngredientPriceDto> givenIngredientPriceDtos() {
    IngredientPriceDto anIngredientPriceDto = new IngredientPriceDto(AN_INGREDIENT_NAME_STRINGIFY,
                                                                     A_PRICE);
    IngredientPriceDto anotherIngredientPriceDto = new IngredientPriceDto(ANOTHER_INGREDIENT_NAME_STRINGIFY,
                                                                          ANOTHER_PRICE);
    return Arrays.asList(anIngredientPriceDto, anotherIngredientPriceDto);
  }

  private Map<IngredientName, BigDecimal> givenExpectedIngredientNameToPrice() {
    Map<IngredientName, BigDecimal> expectedIngredientNameToPrice = new HashMap<>();
    expectedIngredientNameToPrice.put(AN_INGREDIENT_NAME, A_PRICE);
    expectedIngredientNameToPrice.put(ANOTHER_INGREDIENT_NAME, ANOTHER_PRICE);
    return expectedIngredientNameToPrice;
  }
}
