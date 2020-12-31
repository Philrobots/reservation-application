package ca.ulaval.glo4002.reservation.api.report.presenter.unit;

import static com.google.common.truth.Truth.assertThat;
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

import ca.ulaval.glo4002.reservation.api.report.dto.UnitReportDayDto;
import ca.ulaval.glo4002.reservation.api.report.dto.UnitReportDto;
import ca.ulaval.glo4002.reservation.api.report.presenter.IngredientReportInformationDto;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.report.DailyIngredientReportInformation;
import ca.ulaval.glo4002.reservation.domain.report.IngredientReport;

@ExtendWith(MockitoExtension.class)
public class UnitIngredientReportDtoFactoryTest {
  private static final LocalDate FIRST_DATE = LocalDate.of(2150, 7, 23);
  private static final LocalDate LATER_DATE = LocalDate.of(2150, 8, 3);

  private static final BigDecimal A_DAILY_TOTAL_PRICE = BigDecimal.valueOf(3242.5432);
  private static final BigDecimal ANOTHER_DAILY_TOTAL_PRICE = BigDecimal.valueOf(216.8321);

  private static final BigDecimal PEPPERONI_QUANTITY = BigDecimal.valueOf(7654.235);
  private static final BigDecimal PEPPERONI_PRICE = BigDecimal.valueOf(312.546);
  private static final BigDecimal KIMCHI_QUANTITY = BigDecimal.valueOf(98723.4);
  private static final BigDecimal KIMCHI_PRICE = BigDecimal.valueOf(123);

  @Mock
  private IngredientReport ingredientReport;

  @Mock
  private DailyIngredientReportInformation aDailyIngredientReportInformation;

  @Mock
  private DailyIngredientReportInformation anotherDailyIngredientReportInformation;

  @Mock
  private UnitReportDayDtoFactory unitReportDayDtoFactory;

  private UnitReportDtoFactory unitReportDtoFactory;

  @BeforeEach
  public void setUp() {
    unitReportDtoFactory = new UnitReportDtoFactory(unitReportDayDtoFactory);
  }

  @Test
  public void givenReport_whenCreate_thenDailyIngredientInformationAreFetched() {
    // when
    unitReportDtoFactory.create(ingredientReport);

    // then
    verify(ingredientReport).getDailyIngredientsInformation();
  }

  @Test
  public void givenReport_whenCreate_thenCorrespondingUnitReportDayDtosAreCreated() {
    // given
    Map<LocalDate, DailyIngredientReportInformation> givenDailyIngredientReportInformation = givenDailyIngredientReportInformation();
    given(ingredientReport.getDailyIngredientsInformation()).willReturn(givenDailyIngredientReportInformation);
    given(unitReportDayDtoFactory.create(LATER_DATE,
                                         aDailyIngredientReportInformation)).willReturn(givenAnUnitReportDayDto());
    given(unitReportDayDtoFactory.create(FIRST_DATE,
                                         anotherDailyIngredientReportInformation)).willReturn(givenAnotherUnitReportDayDto());

    // when
    unitReportDtoFactory.create(ingredientReport);

    // then
    verify(unitReportDayDtoFactory).create(LATER_DATE, aDailyIngredientReportInformation);
    verify(unitReportDayDtoFactory).create(FIRST_DATE, anotherDailyIngredientReportInformation);
  }

  @Test
  public void givenReport_whenCreate_thenUnitReportDtoIsCorrectlyCreated() {
    Map<LocalDate, DailyIngredientReportInformation> givenDailyIngredientReportInformation = givenDailyIngredientReportInformation();
    given(ingredientReport.getDailyIngredientsInformation()).willReturn(givenDailyIngredientReportInformation);
    UnitReportDayDto anUnitReportDayDto = givenAnUnitReportDayDto();
    UnitReportDayDto anotherUnitReportDayDto = givenAnotherUnitReportDayDto();
    given(unitReportDayDtoFactory.create(LATER_DATE,
                                         aDailyIngredientReportInformation)).willReturn(anUnitReportDayDto);
    given(unitReportDayDtoFactory.create(FIRST_DATE,
                                         anotherDailyIngredientReportInformation)).willReturn(anotherUnitReportDayDto);

    // when
    UnitReportDto unitReportDto = unitReportDtoFactory.create(ingredientReport);

    // then
    assertThat(unitReportDto.getUnitReportDayDtos()).contains(anUnitReportDayDto);
    assertThat(unitReportDto.getUnitReportDayDtos()).contains(anotherUnitReportDayDto);
  }

  @Test
  public void givenReport_whenCreate_thenUnitReportDayDtosAreInChronologicalOrder() {
    Map<LocalDate, DailyIngredientReportInformation> givenDailyIngredientReportInformation = givenDailyIngredientReportInformation();
    given(ingredientReport.getDailyIngredientsInformation()).willReturn(givenDailyIngredientReportInformation);
    UnitReportDayDto anUnitReportDayDto = givenAnUnitReportDayDto();
    UnitReportDayDto anotherUnitReportDayDto = givenAnotherUnitReportDayDto();
    given(unitReportDayDtoFactory.create(LATER_DATE,
                                         aDailyIngredientReportInformation)).willReturn(anUnitReportDayDto);
    given(unitReportDayDtoFactory.create(FIRST_DATE,
                                         anotherDailyIngredientReportInformation)).willReturn(anotherUnitReportDayDto);

    // when
    UnitReportDto unitReportDto = unitReportDtoFactory.create(ingredientReport);

    // then
    assertThat(unitReportDto.getUnitReportDayDtos()
                            .get(0)
                            .getDate()).isEqualTo(FIRST_DATE.toString());
    assertThat(unitReportDto.getUnitReportDayDtos()
                            .get(1)
                            .getDate()).isEqualTo(LATER_DATE.toString());
  }

  private Map<LocalDate, DailyIngredientReportInformation> givenDailyIngredientReportInformation() {
    Map<LocalDate, DailyIngredientReportInformation> dateToDailyIngredientReportInformation = new HashMap<>();
    dateToDailyIngredientReportInformation.put(LATER_DATE, aDailyIngredientReportInformation);
    dateToDailyIngredientReportInformation.put(FIRST_DATE, anotherDailyIngredientReportInformation);
    return dateToDailyIngredientReportInformation;
  }

  private UnitReportDayDto givenAnUnitReportDayDto() {
    List<IngredientReportInformationDto> ingredientReportInformationDtos = Arrays.asList(new IngredientReportInformationDto(IngredientName.PEPPERONI.toString(),
                                                                                                                            PEPPERONI_QUANTITY,
                                                                                                                            PEPPERONI_PRICE));
    return new UnitReportDayDto(FIRST_DATE, ingredientReportInformationDtos, A_DAILY_TOTAL_PRICE);
  }

  private UnitReportDayDto givenAnotherUnitReportDayDto() {
    List<IngredientReportInformationDto> anotherIngredientReportInformationDtos = Arrays.asList(new IngredientReportInformationDto(IngredientName.KIMCHI.toString(),
                                                                                                                                   KIMCHI_QUANTITY,
                                                                                                                                   KIMCHI_PRICE));
    return new UnitReportDayDto(LATER_DATE,
                                anotherIngredientReportInformationDtos,
                                ANOTHER_DAILY_TOTAL_PRICE);
  }
}
