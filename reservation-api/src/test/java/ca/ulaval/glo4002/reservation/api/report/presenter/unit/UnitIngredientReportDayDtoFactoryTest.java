package ca.ulaval.glo4002.reservation.api.report.presenter.unit;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.api.report.dto.UnitReportDayDto;
import ca.ulaval.glo4002.reservation.api.report.presenter.IngredientReportInformationDto;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.report.DailyIngredientReportInformation;
import ca.ulaval.glo4002.reservation.domain.report.IngredientReportInformation;

@ExtendWith(MockitoExtension.class)
public class UnitIngredientReportDayDtoFactoryTest {
  private static final LocalDate A_DATE = LocalDate.of(2150, 7, 23);
  private static final BigDecimal PEPPERONI_QUANTITY = BigDecimal.valueOf(76.235);
  private static final BigDecimal PEPPERONI_PRICE = BigDecimal.valueOf(12.53);
  private static final BigDecimal KIMCHI_QUANTITY = BigDecimal.valueOf(9.476);
  private static final BigDecimal KIMCHI_PRICE = BigDecimal.valueOf(1.1035);
  private static final BigDecimal EXPECTED_DAILY_TOTAL_PRICE = BigDecimal.valueOf(13.6335);

  @Mock
  private DailyIngredientReportInformation dailyIngredientReportInformation;

  private UnitReportDayDtoFactory unitReportDayDtoFactory;

  @BeforeEach
  public void setUp() {
    unitReportDayDtoFactory = new UnitReportDayDtoFactory();
  }

  @Test
  public void whenCreate_thenDailyPriceIsCalculated() {
    // when
    unitReportDayDtoFactory.create(A_DATE, dailyIngredientReportInformation);

    // then
    verify(dailyIngredientReportInformation).calculateDailyTotalPrice();
  }

  @Test
  public void givenADateAndDailyIngredientReportInformation_whenCreate_thenCorrespondingUnitReportDayDtoIsCreated() {
    // given
    DailyIngredientReportInformation dailyIngredientReportInformation = givenADailyIngredientReportInformation();
    List<IngredientReportInformationDto> expectedIngredientReportInformationDtos = givenExpectedIngredientReportInformationDto();

    // when
    UnitReportDayDto unitReportDayDto = unitReportDayDtoFactory.create(A_DATE,
                                                                       dailyIngredientReportInformation);

    // then
    assertThat(unitReportDayDto.getDate()).isEqualTo(A_DATE.toString());
    assertThat(unitReportDayDto.getIngredients()).containsExactlyElementsIn(expectedIngredientReportInformationDtos);
    assertThat(unitReportDayDto.getTotalPrice()).isEquivalentAccordingToCompareTo(EXPECTED_DAILY_TOTAL_PRICE);
  }

  private DailyIngredientReportInformation givenADailyIngredientReportInformation() {
    Set<IngredientReportInformation> ingredientReportInformation = new HashSet<>();
    IngredientReportInformation anIngredientReportInformation = new IngredientReportInformation(IngredientName.PEPPERONI,
                                                                                                PEPPERONI_QUANTITY,
                                                                                                PEPPERONI_PRICE);
    IngredientReportInformation anotherIngredientReportInformation = new IngredientReportInformation(IngredientName.KIMCHI,
                                                                                                     KIMCHI_QUANTITY,
                                                                                                     KIMCHI_PRICE);
    ingredientReportInformation.add(anIngredientReportInformation);
    ingredientReportInformation.add(anotherIngredientReportInformation);
    return new DailyIngredientReportInformation(ingredientReportInformation);
  }

  private List<IngredientReportInformationDto> givenExpectedIngredientReportInformationDto() {
    IngredientReportInformationDto ingredientReportInformationDto = new IngredientReportInformationDto(IngredientName.PEPPERONI.toString(),
                                                                                                       PEPPERONI_QUANTITY,
                                                                                                       PEPPERONI_PRICE);
    IngredientReportInformationDto anotherIngredientReportInformationDto = new IngredientReportInformationDto(IngredientName.KIMCHI.toString(),
                                                                                                              KIMCHI_QUANTITY,
                                                                                                              KIMCHI_PRICE);
    return Arrays.asList(ingredientReportInformationDto, anotherIngredientReportInformationDto);
  }
}
