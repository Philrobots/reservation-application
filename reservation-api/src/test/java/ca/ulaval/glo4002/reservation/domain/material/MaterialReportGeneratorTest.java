package ca.ulaval.glo4002.reservation.domain.material;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.report.ReportPeriod;

@ExtendWith(MockitoExtension.class)
public class MaterialReportGeneratorTest {

  private static final LocalDate A_DATE = LocalDate.EPOCH;
  private static final LocalDate A_LATER_DATE = LocalDate.EPOCH.plusDays(1);
  private static final LocalDate ANOTHER_LATER_DATE = LocalDate.EPOCH.plusDays(2);
  private static final BigDecimal FEW_DISHES = BigDecimal.valueOf(5);
  private static final BigDecimal MORE_THAN_A_FEW_DISHES = BigDecimal.valueOf(7);
  private static final BigDecimal LESS_THAN_A_FEW_DISHES = BigDecimal.valueOf(2);
  private static final BigDecimal EXPECTED_BOUGHT_QUANTITY = BigDecimal.valueOf(2);
  private static final BigDecimal A_PRICE = BigDecimal.TEN;

  @Mock
  private CleanMaterialPriceCalculator cleanMaterialPriceCalculator;

  @Mock
  private MaterialToBuyPriceCalculator materialToBuyPriceCalculator;

  @Mock
  private DailyDishesQuantity aDailyDishesQuantity;

  @Mock
  private DailyDishesQuantity anotherDailyDishesQuantity;

  @Mock
  private DailyDishesQuantity laterDailyDishesQuantity;

  @Mock
  private ReportPeriod reportPeriod;

  private MaterialReportGenerator materialReportGenerator;

  @BeforeEach
  public void setUp() {
    materialReportGenerator = new MaterialReportGenerator(cleanMaterialPriceCalculator,
                                                          materialToBuyPriceCalculator);
  }

  @Test
  public void givenFirstReportDateIsBeforeReportPeriodStartDate_whenGenerateReport_thenAllMaterialReportInformationDatesAreInReportPeriod() {
    // given
    given(reportPeriod.getStartDate()).willReturn(A_LATER_DATE);
    given(materialToBuyPriceCalculator.calculateBuyPrice(any())).willReturn(A_PRICE);
    given(cleanMaterialPriceCalculator.calculateCleaningPrice(any())).willReturn(A_PRICE);
    Map<LocalDate, DailyDishesQuantity> dailyDishesQuantity = givenThreeConsecutiveDatesWithAnyAmountOfDishes();

    // when
    MaterialReport materialReport = materialReportGenerator.generateReport(dailyDishesQuantity,
                                                                           reportPeriod);
    MaterialReportInformation firstDateMaterialReportInformation = materialReport.getMaterialReportInformation()
                                                                                 .get(0);
    MaterialReportInformation secondDateMaterialReportInformation = materialReport.getMaterialReportInformation()
                                                                                  .get(1);

    // then
    assertThat(firstDateMaterialReportInformation.getDate()).isAtLeast(reportPeriod.getStartDate());
    assertThat(secondDateMaterialReportInformation.getDate()).isAtLeast(reportPeriod.getStartDate());
  }

  @Test
  public void whenGetTotalPrice_thenCleanAndBuyCalculatorsAreCalled() {
    // given
    given(reportPeriod.getStartDate()).willReturn(A_LATER_DATE);
    given(materialToBuyPriceCalculator.calculateBuyPrice(any())).willReturn(A_PRICE);
    given(cleanMaterialPriceCalculator.calculateCleaningPrice(any())).willReturn(A_PRICE);
    Map<LocalDate, DailyDishesQuantity> dailyDishesQuantities = givenBuyAndCleanDishes();

    // when
    materialReportGenerator.generateReport(dailyDishesQuantities, reportPeriod);

    // then
    verify(materialToBuyPriceCalculator, times(2)).calculateBuyPrice(any());
    verify(cleanMaterialPriceCalculator, times(2)).calculateCleaningPrice(any());
  }

  @Test
  public void whenGenerateMaterialReportInformation_thenDatesAreInChronologicalOrder() {
    // given
    given(reportPeriod.getStartDate()).willReturn(A_DATE);
    given(materialToBuyPriceCalculator.calculateBuyPrice(any())).willReturn(A_PRICE);
    given(cleanMaterialPriceCalculator.calculateCleaningPrice(any())).willReturn(A_PRICE);
    Map<LocalDate, DailyDishesQuantity> dailyDishesQuantity = givenTwoDatesWithEqualDishes();

    // when
    MaterialReport materialReport = materialReportGenerator.generateReport(dailyDishesQuantity,
                                                                           reportPeriod);

    // then
    MaterialReportInformation firstDateMaterialReportInformation = materialReport.getMaterialReportInformation()
                                                                                 .get(0);
    MaterialReportInformation secondDateMaterialReportInformation = materialReport.getMaterialReportInformation()
                                                                                  .get(1);
    assertThat(firstDateMaterialReportInformation.getDate()).isLessThan(secondDateMaterialReportInformation.getDate());
  }

  @Test
  public void givenEmptyReportDates_whenGenerateMaterialReportInformation_thenReturnEmptyMaterialReportInformation() {
    // given
    Map<LocalDate, DailyDishesQuantity> dailyDishesQuantity = new HashMap<>();

    // when
    MaterialReport materialReport = materialReportGenerator.generateReport(dailyDishesQuantity,
                                                                           reportPeriod);

    // then
    assertThat(materialReport.getMaterialReportInformation()).isEmpty();
  }

  @Test
  public void givenOneDateInReportDates_whenGenerateMaterialReportInformation_thenDishesAreBought() {
    // given
    given(reportPeriod.getStartDate()).willReturn(A_DATE);
    given(materialToBuyPriceCalculator.calculateBuyPrice(any())).willReturn(A_PRICE);
    given(cleanMaterialPriceCalculator.calculateCleaningPrice(any())).willReturn(A_PRICE);
    Map<Material, BigDecimal> EMPTY_DISHES = givenDishesQuantity(BigDecimal.ZERO);
    Map<Material, BigDecimal> expectedBoughtDishes = expectedBoughtDishes();
    Map<LocalDate, DailyDishesQuantity> dailyDishesQuantity = givenOneDateInReportDates();

    // when
    MaterialReport materialReport = materialReportGenerator.generateReport(dailyDishesQuantity,
                                                                           reportPeriod);

    // then
    MaterialReportInformation dateMaterialReportInformation = materialReport.getMaterialReportInformation()
                                                                            .get(0);
    assertThat(dateMaterialReportInformation.getBoughtDishes()).containsExactlyEntriesIn(expectedBoughtDishes);
    assertThat(dateMaterialReportInformation.getCleanedDishes()).containsExactlyEntriesIn(EMPTY_DISHES);
  }

  @Test
  public void givenTwoDatesWithEqualDishes_whenGenerateMaterialReportInformation_thenDishesAreCleaned() {
    // given
    given(reportPeriod.getStartDate()).willReturn(A_DATE);
    given(materialToBuyPriceCalculator.calculateBuyPrice(any())).willReturn(A_PRICE);
    given(cleanMaterialPriceCalculator.calculateCleaningPrice(any())).willReturn(A_PRICE);
    Map<Material, BigDecimal> expectedBoughtDishes = givenDishesQuantity(BigDecimal.ZERO);
    Map<Material, BigDecimal> expectedCleanedDishes = givenDishesQuantity(FEW_DISHES);
    Map<LocalDate, DailyDishesQuantity> dailyDishesQuantity = givenTwoDatesWithEqualDishes();

    // when
    MaterialReport materialReport = materialReportGenerator.generateReport(dailyDishesQuantity,
                                                                           reportPeriod);

    // then
    MaterialReportInformation secondDateMaterialReportInformation = materialReport.getMaterialReportInformation()
                                                                                  .get(1);
    assertThat(secondDateMaterialReportInformation.getBoughtDishes()).containsExactlyEntriesIn(expectedBoughtDishes);
    assertThat(secondDateMaterialReportInformation.getCleanedDishes()).containsExactlyEntriesIn(expectedCleanedDishes);
  }

  @Test
  public void givenTwoDatesWithIncreasingAmountOfDishes_whenGenerateMaterialReportInformation_thenDishesAreBoughtAndCleanedAdequately() {
    // given
    given(reportPeriod.getStartDate()).willReturn(A_DATE);
    given(materialToBuyPriceCalculator.calculateBuyPrice(any())).willReturn(A_PRICE);
    given(cleanMaterialPriceCalculator.calculateCleaningPrice(any())).willReturn(A_PRICE);
    Map<Material, BigDecimal> expectedBoughtDishes = givenDishesQuantity(EXPECTED_BOUGHT_QUANTITY);
    Map<Material, BigDecimal> expectedCleanedDishes = givenDishesQuantity(FEW_DISHES);
    Map<LocalDate, DailyDishesQuantity> dailyDishesQuantity = givenTwoDatesWithIncreasingAmountOfDishes();

    // when
    MaterialReport materialReport = materialReportGenerator.generateReport(dailyDishesQuantity,
                                                                           reportPeriod);
    // then
    MaterialReportInformation secondDateMaterialReportInformation = materialReport.getMaterialReportInformation()
                                                                                  .get(1);
    assertThat(secondDateMaterialReportInformation.getBoughtDishes()).containsExactlyEntriesIn(expectedBoughtDishes);
    assertThat(secondDateMaterialReportInformation.getCleanedDishes()).containsExactlyEntriesIn(expectedCleanedDishes);
  }

  @Test
  public void givenTwoDatesWithDecreasingAmountOfDishes_whenGenerateMaterialReportInformation_thenDishesAreCleanedAdequately() {
    // given
    given(reportPeriod.getStartDate()).willReturn(A_DATE);
    given(materialToBuyPriceCalculator.calculateBuyPrice(any())).willReturn(A_PRICE);
    given(cleanMaterialPriceCalculator.calculateCleaningPrice(any())).willReturn(A_PRICE);
    Map<Material, BigDecimal> expectedCleanedDishes = givenDishesQuantity(LESS_THAN_A_FEW_DISHES);
    Map<Material, BigDecimal> expectedDishes = givenDishesQuantity(BigDecimal.ZERO);
    Map<LocalDate, DailyDishesQuantity> dailyDishesQuantity = givenTwoDatesWithDecreasingAmountOfDishes();

    // when
    List<MaterialReportInformation> materialReportInformation = materialReportGenerator.generateReport(dailyDishesQuantity,
                                                                                                       reportPeriod)
                                                                                       .getMaterialReportInformation();

    // then
    MaterialReportInformation secondDateMaterialReportInformation = materialReportInformation.get(1);
    assertThat(secondDateMaterialReportInformation.getBoughtDishes()).containsExactlyEntriesIn(expectedDishes);
    assertThat(secondDateMaterialReportInformation.getCleanedDishes()).containsExactlyEntriesIn(expectedCleanedDishes);
  }

  private Map<LocalDate, DailyDishesQuantity> givenTwoDatesWithDecreasingAmountOfDishes() {
    Map<Material, BigDecimal> firstDayDishes = givenFewDishes();
    Map<Material, BigDecimal> secondDayDishes = getLessThanAFewDishes();
    given(aDailyDishesQuantity.getDishesQuantity()).willReturn(firstDayDishes);
    given(anotherDailyDishesQuantity.getDishesQuantity()).willReturn(secondDayDishes);
    return Map.of(A_DATE, aDailyDishesQuantity, A_LATER_DATE, anotherDailyDishesQuantity);
  }

  private Map<Material, BigDecimal> getLessThanAFewDishes() {
    return givenDishesQuantity(LESS_THAN_A_FEW_DISHES);
  }

  private Map<LocalDate, DailyDishesQuantity> givenTwoDatesWithEqualDishes() {
    Map<Material, BigDecimal> dailyDishes = givenFewDishes();
    given(aDailyDishesQuantity.getDishesQuantity()).willReturn(dailyDishes);
    return Map.of(A_DATE, aDailyDishesQuantity, A_LATER_DATE, aDailyDishesQuantity);
  }

  private Map<LocalDate, DailyDishesQuantity> givenOneDateInReportDates() {
    Map<Material, BigDecimal> dailyDishes = givenFewDishes();
    given(aDailyDishesQuantity.getDishesQuantity()).willReturn(dailyDishes);
    return Map.of(A_DATE, aDailyDishesQuantity);
  }

  private Map<LocalDate, DailyDishesQuantity> givenTwoDatesWithIncreasingAmountOfDishes() {
    Map<Material, BigDecimal> firstDayDishes = givenFewDishes();
    Map<Material, BigDecimal> secondDayDishes = givenMoreThanAFewDishes();
    given(aDailyDishesQuantity.getDishesQuantity()).willReturn(firstDayDishes);
    given(anotherDailyDishesQuantity.getDishesQuantity()).willReturn(secondDayDishes);
    return Map.of(A_DATE, aDailyDishesQuantity, A_LATER_DATE, anotherDailyDishesQuantity);
  }

  private Map<Material, BigDecimal> givenFewDishes() {
    return givenDishesQuantity(FEW_DISHES);
  }

  private Map<Material, BigDecimal> givenMoreThanAFewDishes() {
    return givenDishesQuantity(MORE_THAN_A_FEW_DISHES);
  }

  private Map<Material, BigDecimal> expectedBoughtDishes() {
    return givenFewDishes();
  }

  private Map<LocalDate, DailyDishesQuantity> givenThreeConsecutiveDatesWithAnyAmountOfDishes() {
    Map<Material, BigDecimal> anyDishes = givenFewDishes();
    given(aDailyDishesQuantity.getDishesQuantity()).willReturn(anyDishes);
    given(anotherDailyDishesQuantity.getDishesQuantity()).willReturn(anyDishes);
    given(laterDailyDishesQuantity.getDishesQuantity()).willReturn(anyDishes);
    return Map.of(A_DATE, aDailyDishesQuantity, A_LATER_DATE, anotherDailyDishesQuantity, ANOTHER_LATER_DATE, laterDailyDishesQuantity);
  }

  private Map<Material, BigDecimal> givenDishesQuantity(BigDecimal dishQuantity) {
    return Map.of(Material.FORK, dishQuantity, Material.BOWL, dishQuantity, Material.KNIFE, dishQuantity, Material.PLATE,
                  dishQuantity, Material.SPOON, dishQuantity);
  }

  private Map<LocalDate, DailyDishesQuantity> givenBuyAndCleanDishes() {
    Map<Material, BigDecimal> firstDayDishes = givenDishesQuantity(BigDecimal.valueOf(3));
    Map<Material, BigDecimal> secondDayDishes = givenDishesQuantity(BigDecimal.valueOf(6));
    given(aDailyDishesQuantity.getDishesQuantity()).willReturn(firstDayDishes);
    given(anotherDailyDishesQuantity.getDishesQuantity()).willReturn(secondDayDishes);
    return Map.of(A_DATE, aDailyDishesQuantity, A_LATER_DATE, anotherDailyDishesQuantity);
  }
}
