package ca.ulaval.glo4002.reservation.domain.report;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import ca.ulaval.glo4002.reservation.domain.date.DinerPeriod;
import ca.ulaval.glo4002.reservation.domain.report.exception.InvalidReportDateException;

public class ReportDinerPeriodFactoryTest {
  private static final LocalDate A_DINNER_BEGIN_DATE = LocalDate.of(2150, 7, 20);
  private static final LocalDate A_DINNER_END_DATE = LocalDate.of(2150, 7, 30);
  private static final LocalDate A_REPORT_DATE_BEFORE_DINNER_DATE = LocalDate.of(2150, 7, 19);
  private static final LocalDate A_REPORT_DATE_AFTER_DINNER_DATE = LocalDate.of(2150, 7, 31);
  private static final LocalDate A_START_REPORT_DATE = LocalDate.of(2150, 7, 21);
  private static final LocalDate A_END_REPORT_DATE = LocalDate.of(2150, 7, 29);

  private final DinerPeriod dinnerDinerPeriod = new DinerPeriod(A_DINNER_BEGIN_DATE, A_DINNER_END_DATE);

  private ReportPeriodFactory reportPeriodFactory;

  @BeforeEach
  public void setUpReportPeriodFactory() {
    reportPeriodFactory = new ReportPeriodFactory();
  }

  @Test
  public void givenStartDateBeingBeforeDinnerPeriod_whenCreate_thenThrowInvalidReportDateException() {
    // when
    Executable initializingReportPeriod = () -> reportPeriodFactory.create(A_REPORT_DATE_BEFORE_DINNER_DATE,
                                                                           A_END_REPORT_DATE,
            dinnerDinerPeriod);

    // then
    assertThrows(InvalidReportDateException.class, initializingReportPeriod);
  }

  @Test
  public void givenEndDateBeingAfterDinnerPeriod_whenCreate_thenThrowInvalidReportDateException() {
    // when
    Executable initializingReportPeriod = () -> reportPeriodFactory.create(A_START_REPORT_DATE,
                                                                           A_REPORT_DATE_AFTER_DINNER_DATE,
            dinnerDinerPeriod);

    // then
    assertThrows(InvalidReportDateException.class, initializingReportPeriod);
  }

  @Test
  public void givenAValidReportPeriod_whenCreate_thenTheReportPeriodIsCreated() {
    // when
    ReportPeriod reportPeriod = reportPeriodFactory.create(A_START_REPORT_DATE,
                                                           A_END_REPORT_DATE,
            dinnerDinerPeriod);

    assertThat(reportPeriod.getStartDate()).isEqualTo(A_START_REPORT_DATE);
    assertThat(reportPeriod.getEndDate()).isEqualTo(A_END_REPORT_DATE);

  }
}
