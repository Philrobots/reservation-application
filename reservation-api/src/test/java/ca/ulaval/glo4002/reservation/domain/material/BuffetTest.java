package ca.ulaval.glo4002.reservation.domain.material;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.date.DinerPeriod;
import ca.ulaval.glo4002.reservation.domain.report.ReportPeriod;
import ca.ulaval.glo4002.reservation.domain.reservation.Reservation;

@ExtendWith(MockitoExtension.class)
public class BuffetTest {
  private final static int A_CUSTOMER_COUNT = 4;
  private final static int A_RESTRICTION_COUNT = 5;
  private final static int ANOTHER_CUSTOMER_COUNT = 4356;
  private final static int ANOTHER_RESTRICTION_COUNT = 23;
  private final static LocalDateTime LOCAL_DATE_TIME_DINNER_DATE = LocalDateTime.of(2150,
                                                                                    7,
                                                                                    3,
                                                                                    4,
                                                                                    4);
  private final static LocalDate LOCAL_DATE_DINNER_DATE = LOCAL_DATE_TIME_DINNER_DATE.toLocalDate();
  private final static LocalDate REPORT_PERIOD_START = LocalDate.of(2150, 7, 23);

  private final static LocalDate REPORT_PERIOD_END = LocalDate.of(2150, 7, 25);
  private final static LocalDateTime DINNER_DATE_BEFORE_REPORT_PERIOD = LocalDateTime.of(2150,
                                                                                         7,
                                                                                         22,
                                                                                         3,
                                                                                         5);
  private final static LocalDateTime DINNER_DATE_AFTER_REPORT_PERIOD = LocalDateTime.of(2150,
                                                                                        7,
                                                                                        27,
                                                                                        23,
                                                                                        1);
  private final static LocalDateTime A_DINNER_DATE_WITHIN_REPORT_PERIOD = LocalDateTime.of(2150,
                                                                                           7,
                                                                                           25,
                                                                                           21,
                                                                                           4);
  private final static LocalDateTime ANOTHER_DINNER_DATE_WITHIN_REPORT_PERIOD = LocalDateTime.of(2150,
                                                                                                 7,
                                                                                                 24,
                                                                                                 9,
                                                                                                 2);

  @Mock(lenient = true)
  private Reservation aReservation;

  @Mock
  private Reservation anotherReservation;

  @Mock
  private Reservation reservationBeforeReportPeriod;

  @Mock
  private DailyDishesQuantityFactory dailyDishesQuantityFactory;

  @Mock
  private DailyDishesQuantity dailyDishesQuantity;

  @Mock
  private DailyDishesQuantity anotherDailyDishesQuantity;

  @Mock
  private DailyDishesQuantity lastDailyDishesQuantityBeforeReportPeriodStart;

  private DinerPeriod dinerPeriod = new DinerPeriod(REPORT_PERIOD_START, REPORT_PERIOD_END);

  private Buffet buffet;

  @BeforeEach
  public void setUp() {
    givenAReservationWithDinnerDate(LOCAL_DATE_TIME_DINNER_DATE);
    buffet = new Buffet(dailyDishesQuantityFactory);
  }

  @Test
  public void givenEmptyBuffet_whenUpdateDailyDishesQuantity_thenDailyDishesQuantityIsCreatedForReservation() {
    // given
    givenAReservationWithDinnerDate(LOCAL_DATE_TIME_DINNER_DATE);

    // when
    buffet.updateDailyDishesQuantity(aReservation);

    // then
    verify(dailyDishesQuantityFactory).create(aReservation);
  }

  @Test
  public void whenUpdateDailyDishesQuantity_thenDinnerDateIsFetchFromReservation() {
    // when
    buffet.updateDailyDishesQuantity(aReservation);

    // then
    verify(aReservation).getDinnerDate();
  }

  @Test
  public void givenNoMaterialQuantityAtDinnerDate_whenUpdateDailyDishesQuantity_thenDailyDishesQuantityIsCreatedAtDinnerDate() {
    // given
    givenAReservationWithDinnerDate(LOCAL_DATE_TIME_DINNER_DATE);
    given(dailyDishesQuantityFactory.create(aReservation)).willReturn(dailyDishesQuantity);

    // when
    buffet.updateDailyDishesQuantity(aReservation);

    // then
    assertThat(buffet.getAllDailyDishesQuantity()
                     .get(LOCAL_DATE_DINNER_DATE)).isEqualTo(dailyDishesQuantity);

  }

  @Test
  public void givenExistingDailyDishesQuantityAtDinnerDate_whenUpdateDailyDishesQuantity_thenExistingDailyDishesQuantityIsUpdated() {
    // given
    given(dailyDishesQuantityFactory.create(aReservation)).willReturn(dailyDishesQuantity);
    buffet.updateDailyDishesQuantity(aReservation);
    given(anotherReservation.getDinnerDate()).willReturn(LOCAL_DATE_TIME_DINNER_DATE);
    given(anotherReservation.getNumberOfCustomers()).willReturn(ANOTHER_CUSTOMER_COUNT);
    given(anotherReservation.getNumberOfRestrictions()).willReturn(ANOTHER_RESTRICTION_COUNT);

    // when
    buffet.updateDailyDishesQuantity(anotherReservation);

    // then
    verify(dailyDishesQuantity).updateQuantity(ANOTHER_CUSTOMER_COUNT, ANOTHER_RESTRICTION_COUNT);
  }

  @Test
  public void givenNoExistingDishesQuantity_whenGetDishesQuantity_thenDoNotReturnAnyDishesQuantity() {
    // given
    ReportPeriod reportPeriod = new ReportPeriod(REPORT_PERIOD_START, REPORT_PERIOD_END);

    // when
    Map<LocalDate, DailyDishesQuantity> dailyDishesQuantities = buffet.getDishesQuantityByDate(reportPeriod);

    // then
    assertThat(dailyDishesQuantities).isEmpty();
  }

  @Test
  public void givenOnlyDishesQuantityWithinReportPeriod_whenGetDishesQuantity_thenReturnAllDishesQuantity() {
    // given
    ReportPeriod reportPeriod = new ReportPeriod(REPORT_PERIOD_START, REPORT_PERIOD_END);
    given(aReservation.getDinnerDate()).willReturn(A_DINNER_DATE_WITHIN_REPORT_PERIOD);
    given(anotherReservation.getDinnerDate()).willReturn(ANOTHER_DINNER_DATE_WITHIN_REPORT_PERIOD);
    given(dailyDishesQuantityFactory.create(aReservation)).willReturn(dailyDishesQuantity);
    given(dailyDishesQuantityFactory.create(anotherReservation)).willReturn(anotherDailyDishesQuantity);
    buffet.updateDailyDishesQuantity(aReservation);
    buffet.updateDailyDishesQuantity(anotherReservation);

    // when
    Map<LocalDate, DailyDishesQuantity> dailyDishesQuantities = buffet.getDishesQuantityByDate(reportPeriod);

    // then
    assertThat(dailyDishesQuantities).containsExactly(A_DINNER_DATE_WITHIN_REPORT_PERIOD.toLocalDate(),
                                                      dailyDishesQuantity,
                                                      ANOTHER_DINNER_DATE_WITHIN_REPORT_PERIOD.toLocalDate(),
                                                      anotherDailyDishesQuantity);

  }

  @Test
  public void givenDailyDishesQuantityWithinReportPeriodAndOneBefore_whenGetDishesQuantity_thenAllDailyDishesQuantityWithinPeriodAndTheLastOneBefore() {
    // given
    ReportPeriod reportPeriod = new ReportPeriod(REPORT_PERIOD_START, REPORT_PERIOD_END);
    given(aReservation.getDinnerDate()).willReturn(A_DINNER_DATE_WITHIN_REPORT_PERIOD);
    given(anotherReservation.getDinnerDate()).willReturn(ANOTHER_DINNER_DATE_WITHIN_REPORT_PERIOD);
    given(reservationBeforeReportPeriod.getDinnerDate()).willReturn(DINNER_DATE_BEFORE_REPORT_PERIOD);
    given(dailyDishesQuantityFactory.create(aReservation)).willReturn(dailyDishesQuantity);
    given(dailyDishesQuantityFactory.create(anotherReservation)).willReturn(anotherDailyDishesQuantity);
    given(dailyDishesQuantityFactory.create(reservationBeforeReportPeriod)).willReturn(lastDailyDishesQuantityBeforeReportPeriodStart);
    buffet.updateDailyDishesQuantity(anotherReservation);
    buffet.updateDailyDishesQuantity(aReservation);
    buffet.updateDailyDishesQuantity(reservationBeforeReportPeriod);

    // when
    Map<LocalDate, DailyDishesQuantity> dailyDishesQuantities = buffet.getDishesQuantityByDate(reportPeriod);

    // then
    Map<LocalDate, DailyDishesQuantity> expectedQuantities = expectedDishesQuantitiesWithOneBeforeReportPeriod();
    assertThat(dailyDishesQuantities).containsExactlyEntriesIn(expectedQuantities);
  }

  @Test
  public void givenOneDailyDishesQuantityWithinReportPeriodAndOneAfter_whenGetDishesQuantity_theReturnTheDishesQuantityWithinTheReportPeriod() {
    // given
    ReportPeriod reportPeriod = new ReportPeriod(REPORT_PERIOD_START, REPORT_PERIOD_END);
    given(aReservation.getDinnerDate()).willReturn(A_DINNER_DATE_WITHIN_REPORT_PERIOD);
    given(anotherReservation.getDinnerDate()).willReturn(DINNER_DATE_AFTER_REPORT_PERIOD);
    given(dailyDishesQuantityFactory.create(aReservation)).willReturn(dailyDishesQuantity);
    given(dailyDishesQuantityFactory.create(anotherReservation)).willReturn(anotherDailyDishesQuantity);
    buffet.updateDailyDishesQuantity(anotherReservation);
    buffet.updateDailyDishesQuantity(aReservation);

    // when
    Map<LocalDate, DailyDishesQuantity> dailyDishesQuantities = buffet.getDishesQuantityByDate(reportPeriod);

    // then
    assertThat(dailyDishesQuantities).containsExactly(A_DINNER_DATE_WITHIN_REPORT_PERIOD.toLocalDate(),
                                                      dailyDishesQuantity);
  }

  private void givenAReservationWithDinnerDate(LocalDateTime dinnerDate) {
    given(aReservation.getNumberOfCustomers()).willReturn(A_CUSTOMER_COUNT);
    given(aReservation.getNumberOfRestrictions()).willReturn(A_RESTRICTION_COUNT);
    given(aReservation.getDinnerDate()).willReturn(dinnerDate);
  }

  private Map<LocalDate, DailyDishesQuantity> expectedDishesQuantitiesWithOneBeforeReportPeriod() {
    Map<LocalDate, DailyDishesQuantity> dishesQuantities = new HashMap<>();
    dishesQuantities.put(A_DINNER_DATE_WITHIN_REPORT_PERIOD.toLocalDate(), dailyDishesQuantity);
    dishesQuantities.put(ANOTHER_DINNER_DATE_WITHIN_REPORT_PERIOD.toLocalDate(),
                         anotherDailyDishesQuantity);
    dishesQuantities.put(DINNER_DATE_BEFORE_REPORT_PERIOD.toLocalDate(),
                         lastDailyDishesQuantityBeforeReportPeriodStart);
    return dishesQuantities;
  }
}
