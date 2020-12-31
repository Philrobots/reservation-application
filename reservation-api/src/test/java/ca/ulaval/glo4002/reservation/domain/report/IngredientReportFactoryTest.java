package ca.ulaval.glo4002.reservation.domain.report;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;

@ExtendWith(MockitoExtension.class)
public class IngredientReportFactoryTest {
  private static final LocalDate A_DATE = LocalDate.of(2150, 7, 23);
  private static final LocalDate ANOTHER_DATE = LocalDate.of(2150, 7, 26);
  private static final BigDecimal PEPPERONI_QUANTITY = BigDecimal.valueOf(234.11);
  private static final BigDecimal KIWI_TOTAL_QUANTITY = BigDecimal.valueOf(24);
  private static final BigDecimal MARMALADE_QUANTITY = BigDecimal.valueOf(3632.452);

  @Mock
  private IngredientPriceCalculator ingredientPriceCalculator;

  @Mock
  private DailyIngredientReportInformation aDailyIngredientReportInformation;

  @Mock
  private DailyIngredientReportInformation anotherDailyIngredientReportInformation;

  @Mock
  private DailyIngredientReportInformationFactory dailyIngredientReportInformationFactory;

  private IngredientReportFactory ingredientReportFactory;

  @BeforeEach
  public void setUpReportFactory() {
    ingredientReportFactory = new IngredientReportFactory(dailyIngredientReportInformationFactory);
  }

  @Test
  public void givenIngredientsQuantities_whenCreate_thenDailyIngredientReportInformationAreCreatedForEachDay() {
    // given
    Map<IngredientName, BigDecimal> someIngredientsQuantities = givenSomeIngredientsQuantities();
    Map<IngredientName, BigDecimal> otherIngredientsQuantities = givenOtherIngredientsQuantities();
    Map<LocalDate, Map<IngredientName, BigDecimal>> dateToIngredientsQuantities = Map.of(A_DATE,
                                                                                         someIngredientsQuantities,
                                                                                         ANOTHER_DATE,
                                                                                         otherIngredientsQuantities);

    // when
    ingredientReportFactory.create(ingredientPriceCalculator, dateToIngredientsQuantities);

    // then
    verify(dailyIngredientReportInformationFactory).create(ingredientPriceCalculator,
                                                           someIngredientsQuantities);
    verify(dailyIngredientReportInformationFactory).create(ingredientPriceCalculator,
                                                           otherIngredientsQuantities);
  }

  @Test
  public void givenIngredientsQuantities_whenCreate_thenReportIsCorrectlyCreated() {
    // given
    Map<IngredientName, BigDecimal> someIngredientsQuantities = givenSomeIngredientsQuantities();
    Map<IngredientName, BigDecimal> otherIngredientsQuantities = givenOtherIngredientsQuantities();
    Map<LocalDate, Map<IngredientName, BigDecimal>> dateToIngredientsQuantities = Map.of(A_DATE,
                                                                                         someIngredientsQuantities,
                                                                                         ANOTHER_DATE,
                                                                                         otherIngredientsQuantities);
    given(dailyIngredientReportInformationFactory.create(ingredientPriceCalculator,
                                                         someIngredientsQuantities)).willReturn(aDailyIngredientReportInformation);
    given(dailyIngredientReportInformationFactory.create(ingredientPriceCalculator,
                                                         otherIngredientsQuantities)).willReturn(anotherDailyIngredientReportInformation);

    // when
    IngredientReport ingredientReport = ingredientReportFactory.create(ingredientPriceCalculator,
                                                                       dateToIngredientsQuantities);

    // then
    assertThat(ingredientReport.getDailyIngredientsInformation()).containsExactlyEntriesIn(Map.of(A_DATE,
                                                                                                  aDailyIngredientReportInformation,
                                                                                                  ANOTHER_DATE,
                                                                                                  anotherDailyIngredientReportInformation));
  }

  private Map<IngredientName, BigDecimal> givenSomeIngredientsQuantities() {
    Map<IngredientName, BigDecimal> ingredientNameToQuantity = new HashMap<>();
    ingredientNameToQuantity.put(IngredientName.PEPPERONI, PEPPERONI_QUANTITY);
    ingredientNameToQuantity.put(IngredientName.KIWI, KIWI_TOTAL_QUANTITY);
    return ingredientNameToQuantity;
  }

  private Map<IngredientName, BigDecimal> givenOtherIngredientsQuantities() {
    Map<IngredientName, BigDecimal> ingredientNameToQuantity = new HashMap<>();
    ingredientNameToQuantity.put(IngredientName.MARMALADE, MARMALADE_QUANTITY);
    return ingredientNameToQuantity;
  }
}
