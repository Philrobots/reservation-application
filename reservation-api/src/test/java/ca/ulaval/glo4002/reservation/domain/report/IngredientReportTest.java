package ca.ulaval.glo4002.reservation.domain.report;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;

@ExtendWith(MockitoExtension.class)
public class IngredientReportTest {
  private static final LocalDate A_DATE = LocalDate.of(2150, 7, 23);
  private static final LocalDate ANOTHER_DATE = LocalDate.of(2150, 7, 24);

  private static final BigDecimal A_DAILY_PRICE = BigDecimal.valueOf(102.425);
  private static final BigDecimal ANOTHER_DAILY_PRICE = BigDecimal.valueOf(978.12);
  private static final BigDecimal EXPECTED_TOTAL_PRICE = BigDecimal.valueOf(1080.545);

  private static final BigDecimal PEPPERONI_QUANTITY = BigDecimal.valueOf(234.11);
  private static final BigDecimal A_DAILY_PEPPERONI_TOTAL_PRICE = BigDecimal.valueOf(1636.4289);
  private static final BigDecimal ANOTHER_PEPPERONI_QUANTITY = BigDecimal.valueOf(122.99);
  private static final BigDecimal ANOTHER_DAILY_PEPPERONI_TOTAL_PRICE = BigDecimal.valueOf(859.7001);
  private static final BigDecimal EXPECTED_PEPPERONI_TOTAL_QUANTITY = BigDecimal.valueOf(357.10);
  private static final BigDecimal EXPECTED_PEPPERONI_TOTAL_PRICE = BigDecimal.valueOf(2496.129);

  private static final BigDecimal KIWI_TOTAL_QUANTITY = BigDecimal.valueOf(24);
  private static final BigDecimal KIWI_TOTAL_PRICE = BigDecimal.valueOf(23.76);

  @Mock
  DailyIngredientReportInformation aDailyIngredientReportInformation;

  @Mock
  DailyIngredientReportInformation anotherDailyIngredientReportInformation;

  @Test
  public void givenDailyIngredientInformation_whenCalculateTotalPriceForEntireReport_thenTotalPriceShouldBeTheSumOfAllDailyPrices() {
    // given
    given(aDailyIngredientReportInformation.calculateDailyTotalPrice()).willReturn(A_DAILY_PRICE);
    given(anotherDailyIngredientReportInformation.calculateDailyTotalPrice()).willReturn(ANOTHER_DAILY_PRICE);
    IngredientReport ingredientReport = new IngredientReport(givenDailyIngredientReportInformationByDate());

    // when
    BigDecimal totalPrice = ingredientReport.calculateTotalPriceForEntireReport();

    // then
    assertThat(totalPrice).isEqualTo(EXPECTED_TOTAL_PRICE);
  }

  @Test
  public void givenDailyIngredientInformation_whenGetTotalIngredientReportInformation_thenShouldCompileAllIngredientInformationForEachIngredient() {
    // given
    DailyIngredientReportInformation aDailyIngredientReportInformation = givenADailyIngredientInformation();
    DailyIngredientReportInformation anotherDailyIngredientReportInformation = givenAnotherDailyIngredientInformation();
    Map<LocalDate, DailyIngredientReportInformation> dateToDailyIngredientReportInformation = Map.of(A_DATE,
                                                                                                     aDailyIngredientReportInformation,
                                                                                                     ANOTHER_DATE,
                                                                                                     anotherDailyIngredientReportInformation);
    IngredientReport ingredientReport = new IngredientReport(dateToDailyIngredientReportInformation);

    // when
    Map<IngredientName, IngredientReportInformation> totalIngredientReportInformation = ingredientReport.generateTotalIngredientReportInformation();

    // then
    assertThat(totalIngredientReportInformation).containsKey(IngredientName.PEPPERONI);
    assertThat(totalIngredientReportInformation.get(IngredientName.PEPPERONI)
                                               .getQuantity()).isEquivalentAccordingToCompareTo(EXPECTED_PEPPERONI_TOTAL_QUANTITY);
    assertThat(totalIngredientReportInformation.get(IngredientName.PEPPERONI)
                                               .getTotalPrice()).isEquivalentAccordingToCompareTo(EXPECTED_PEPPERONI_TOTAL_PRICE);
    assertThat(totalIngredientReportInformation).containsKey(IngredientName.KIWI);
    assertThat(totalIngredientReportInformation.get(IngredientName.KIWI)
                                               .getQuantity()).isEquivalentAccordingToCompareTo(KIWI_TOTAL_QUANTITY);
    assertThat(totalIngredientReportInformation.get(IngredientName.KIWI)
                                               .getTotalPrice()).isEquivalentAccordingToCompareTo(KIWI_TOTAL_PRICE);
  }

  private Map<LocalDate, DailyIngredientReportInformation> givenDailyIngredientReportInformationByDate() {
    Map<LocalDate, DailyIngredientReportInformation> dateToDailyIngredientReportInformation = new HashMap<>();
    dateToDailyIngredientReportInformation.put(A_DATE, aDailyIngredientReportInformation);
    dateToDailyIngredientReportInformation.put(ANOTHER_DATE,
                                               anotherDailyIngredientReportInformation);
    return dateToDailyIngredientReportInformation;
  }

  private DailyIngredientReportInformation givenADailyIngredientInformation() {
    Set<IngredientReportInformation> ingredientsReportInformation = new HashSet<>();
    IngredientReportInformation anIngredientReportInformation = new IngredientReportInformation(IngredientName.PEPPERONI,
                                                                                                PEPPERONI_QUANTITY,
                                                                                                A_DAILY_PEPPERONI_TOTAL_PRICE);
    ingredientsReportInformation.add(anIngredientReportInformation);
    return new DailyIngredientReportInformation(ingredientsReportInformation);
  }

  private DailyIngredientReportInformation givenAnotherDailyIngredientInformation() {
    Set<IngredientReportInformation> ingredientsReportInformation = new HashSet<>();
    IngredientReportInformation anIngredientReportInformation = new IngredientReportInformation(IngredientName.PEPPERONI,
                                                                                                ANOTHER_PEPPERONI_QUANTITY,
                                                                                                ANOTHER_DAILY_PEPPERONI_TOTAL_PRICE);
    IngredientReportInformation anotherIngredientReportInformation = new IngredientReportInformation(IngredientName.KIWI,
                                                                                                     KIWI_TOTAL_QUANTITY,
                                                                                                     KIWI_TOTAL_PRICE);
    ingredientsReportInformation.add(anIngredientReportInformation);
    ingredientsReportInformation.add(anotherIngredientReportInformation);
    return new DailyIngredientReportInformation(ingredientsReportInformation);
  }
}
