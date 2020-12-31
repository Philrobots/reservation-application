package ca.ulaval.glo4002.reservation.domain.report;

import static com.google.common.truth.Truth.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

public class IngredientReportDinerPeriodTest {
  private static final LocalDate VALID_START_DATE = LocalDate.of(2150, 7, 21);
  private static final LocalDate VALID_END_DATE = LocalDate.of(2150, 7, 23);

  private ReportPeriod reportPeriod;

  @Test
  public void whenGetAllDaysOfPeriod_thenReturnedAllTheDaysBetweenStartDateAndEndDate() {
    // given
    reportPeriod = new ReportPeriod(VALID_START_DATE, VALID_END_DATE);
    LocalDate expectedInBetweenDate = LocalDate.of(2150, 7, 22);

    // when
    List<LocalDate> days = reportPeriod.getAllDaysOfPeriod();

    // then
    assertThat(days.contains(VALID_START_DATE)).isTrue();
    assertThat(days.contains(expectedInBetweenDate)).isTrue();
    assertThat(days.contains(VALID_END_DATE)).isTrue();
  }

  @Test
  public void givenTheSameDates_whenGetAllDaysOfPeriod_thenOnlyReturnedTheDate() {
    // given
    ReportPeriod reportPeriod = new ReportPeriod(VALID_START_DATE, VALID_START_DATE);

    // when
    List<LocalDate> days = reportPeriod.getAllDaysOfPeriod();

    // then
    assertThat(days.size()).isEqualTo(1);
    assertThat(days.contains(VALID_START_DATE)).isTrue();
  }
}
