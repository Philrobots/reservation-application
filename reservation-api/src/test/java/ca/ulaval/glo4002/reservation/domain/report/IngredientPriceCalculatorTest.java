package ca.ulaval.glo4002.reservation.domain.report;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.report.exception.IngredientNotFoundException;
import ca.ulaval.glo4002.reservation.infra.report.IngredientPriceDto;

@ExtendWith(MockitoExtension.class)
class IngredientPriceCalculatorTest {
  private static final BigDecimal EXPECTED_TOTAL_PRICE = BigDecimal.valueOf(3.0);
  private static final BigDecimal EXPECTED_PRICE_PER_KG = BigDecimal.valueOf(1.0);
  private static final BigDecimal EXPECTED_INGREDIENT_PRICE = BigDecimal.valueOf(10.0);
  private static final IngredientName AN_INGREDIENT_NAME = IngredientName.BUTTERNUT_SQUASH;
  private static final IngredientName ANOTHER_INGREDIENT_NAME = IngredientName.PORK_LOIN;
  private static final IngredientName INGREDIENT_NAME_NOT_IN_DTO = IngredientName.BACON;
  private static final String INVALID_INGREDIENT_NAME_IN_DTO = "invalidIngredientName";
  private static final BigDecimal ANY_BIG_DECIMAL = BigDecimal.valueOf(3);

  private IngredientPriceCalculator ingredientPriceCalculator;

  @Mock
  private IngredientPriceDto anIngredientPriceDto;

  @Mock
  private IngredientPriceDto anotherIngredientPriceDto;

  @BeforeEach
  public void setUp() {
    ingredientPriceCalculator = new IngredientPriceCalculator();
  }

  @Test
  public void givenDtoIngredients_whenGetTotalPrice_thenReturnCorrectValue() {
    // given
    List<IngredientPriceDto> ingredientPriceDto = givenTwoIngredientsDto();
    ingredientPriceCalculator.generatePriceMapper(ingredientPriceDto);

    // when
    BigDecimal actualTotalPrice = ingredientPriceCalculator.getTotalPrice(ANOTHER_INGREDIENT_NAME,
                                                                          EXPECTED_TOTAL_PRICE);

    // then
    assertThat(actualTotalPrice.doubleValue()).isEqualTo(EXPECTED_TOTAL_PRICE.doubleValue());
  }

  @Test
  public void givenDtoIngredientsWithMissingIngredientName_whenGetTotalPrice_thenShouldThrowIngredientNotFoundException() {
    // given
    List<IngredientPriceDto> ingredientPriceDto = givenTwoIngredientsDto();
    ingredientPriceCalculator.generatePriceMapper(ingredientPriceDto);

    // when
    Executable gettingTotalPrice = () -> ingredientPriceCalculator.getTotalPrice(INGREDIENT_NAME_NOT_IN_DTO,
                                                                                 ANY_BIG_DECIMAL);

    // then
    assertThrows(IngredientNotFoundException.class, gettingTotalPrice);
  }

  @Test
  public void givenInvalidEntryDto_whenGenerateIngredientPriceMapper_thenShouldIgnoreInvalidEntryAndGenerateMapper() {
    // given
    List<IngredientPriceDto> ingredientPriceDto = givenInvalidDto();

    // when
    ingredientPriceCalculator.generatePriceMapper(ingredientPriceDto);

    // then
    assertThat(ingredientPriceCalculator.getTotalPrice(AN_INGREDIENT_NAME,
                                                       BigDecimal.ONE)).isEqualTo(EXPECTED_INGREDIENT_PRICE);
  }

  private List<IngredientPriceDto> givenTwoIngredientsDto() {
    given(anIngredientPriceDto.getName()).willReturn(ANOTHER_INGREDIENT_NAME.toString());
    given(anIngredientPriceDto.getPricePerKg()).willReturn(EXPECTED_PRICE_PER_KG);
    given(anotherIngredientPriceDto.getName()).willReturn(AN_INGREDIENT_NAME.toString());
    given(anotherIngredientPriceDto.getPricePerKg()).willReturn(EXPECTED_INGREDIENT_PRICE);

    return List.of(anIngredientPriceDto, anotherIngredientPriceDto);
  }

  private List<IngredientPriceDto> givenInvalidDto() {
    given(anIngredientPriceDto.getName()).willReturn(INVALID_INGREDIENT_NAME_IN_DTO);
    given(anotherIngredientPriceDto.getName()).willReturn(AN_INGREDIENT_NAME.toString());
    given(anotherIngredientPriceDto.getPricePerKg()).willReturn(EXPECTED_INGREDIENT_PRICE);

    return List.of(anIngredientPriceDto, anotherIngredientPriceDto);
  }

}
