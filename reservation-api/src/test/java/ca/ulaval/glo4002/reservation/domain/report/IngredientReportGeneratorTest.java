package ca.ulaval.glo4002.reservation.domain.report;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.infra.report.IngredientPriceDto;

@ExtendWith(MockitoExtension.class)
public class IngredientReportGeneratorTest {
  private static final String AN_INGREDIENT_NAME_STRINGIFY = IngredientName.BUTTERNUT_SQUASH.toString();
  private static final String ANOTHER_INGREDIENT_NAME_STRINGIFY = IngredientName.CHOCOLATE.toString();
  private static final IngredientName KIWI = IngredientName.KIWI;
  private static final BigDecimal KIWI_QUANTITY = BigDecimal.valueOf(24);
  private static final IngredientName PEPPERONI = IngredientName.PEPPERONI;
  private static final BigDecimal PEPPERONI_QUANTITY = BigDecimal.valueOf(30.983);
  private static final LocalDate A_DATE = LocalDate.of(2050, 7, 20);
  private static final BigDecimal A_PRICE = BigDecimal.valueOf(10.42);
  private static final BigDecimal ANOTHER_PRICE = BigDecimal.valueOf(5.93);

  @Mock
  private IngredientPriceCalculatorFactory ingredientPriceCalculatorFactory;

  @Mock
  private IngredientPriceCalculator ingredientPriceCalculator;

  @Mock
  private IngredientReportFactory ingredientReportFactory;

  private IngredientReportGenerator ingredientReportGenerator;

  @BeforeEach
  public void setUpReportGenerator() {
    ingredientReportGenerator = new IngredientReportGenerator(ingredientPriceCalculatorFactory,
                                                              ingredientReportFactory);
  }

  @Test
  public void whenGenerateReport_thenIngredientPriceCalculatorFactoryIsCalled() {
    // given
    List<IngredientPriceDto> ingredientPriceDtos = givenIngredientPriceDtos();

    // when
    ingredientReportGenerator.generateReport(ingredientPriceDtos, any());

    // then
    verify(ingredientPriceCalculatorFactory).create(ingredientPriceDtos);
  }

  @Test
  public void whenGenerateReport_thenReportFactoryIsCalled() {
    // given
    List<IngredientPriceDto> ingredientPriceDtos = givenIngredientPriceDtos();
    Map<LocalDate, Map<IngredientName, BigDecimal>> dateToIngredientQuantities = givenDateToIngredientQuantities();
    given(ingredientPriceCalculatorFactory.create(ingredientPriceDtos)).willReturn(ingredientPriceCalculator);

    // when
    ingredientReportGenerator.generateReport(ingredientPriceDtos, dateToIngredientQuantities);

    // then
    verify(ingredientReportFactory).create(ingredientPriceCalculator, dateToIngredientQuantities);
  }

  private List<IngredientPriceDto> givenIngredientPriceDtos() {
    IngredientPriceDto anIngredientPriceDto = new IngredientPriceDto(AN_INGREDIENT_NAME_STRINGIFY,
                                                                     A_PRICE);
    IngredientPriceDto anotherIngredientPriceDto = new IngredientPriceDto(ANOTHER_INGREDIENT_NAME_STRINGIFY,
                                                                          ANOTHER_PRICE);
    return Arrays.asList(anIngredientPriceDto, anotherIngredientPriceDto);
  }

  private Map<LocalDate, Map<IngredientName, BigDecimal>> givenDateToIngredientQuantities() {
    Map<LocalDate, Map<IngredientName, BigDecimal>> dateToIngredientQuantities = new HashMap<>();
    dateToIngredientQuantities.put(A_DATE, givenIngredientQuantities());
    return dateToIngredientQuantities;
  }

  private Map<IngredientName, BigDecimal> givenIngredientQuantities() {
    Map<IngredientName, BigDecimal> ingredientQuantities = new HashMap<>();
    ingredientQuantities.put(PEPPERONI, PEPPERONI_QUANTITY);
    ingredientQuantities.put(KIWI, KIWI_QUANTITY);
    return ingredientQuantities;
  }
}
