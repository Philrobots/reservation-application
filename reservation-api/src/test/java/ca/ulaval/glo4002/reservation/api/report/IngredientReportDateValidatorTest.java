package ca.ulaval.glo4002.reservation.api.report;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.api.report.validator.ReportDateValidator;
import ca.ulaval.glo4002.reservation.domain.report.exception.InvalidReportDateException;
import ca.ulaval.glo4002.reservation.service.report.chef.ReportService;

@ExtendWith(MockitoExtension.class)
public class IngredientReportDateValidatorTest {
  private static final String VALID_START_DATE = "2150-05-09";
  private static final String VALID_END_DATE = "2150-06-09";
  private static final String NULL_START_DATE = null;
  private static final String NULL_END_DATE = null;
  private static final String DATE_REGEX = "[0-9]{4}[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])";
  private static final String EMPTY_DATE = "";
  private static final String A_WORD = "nfdsdf";

  @Mock
  private ReportService reportService;

  private ReportDateValidator reportDateValidator;

  @BeforeEach
  public void setUp() {
    reportDateValidator = new ReportDateValidator(DATE_REGEX, reportService);
  }

  @Test
  public void givenValidStartAndEndDates_whenValidate_thenReportDatesAreValid() {
    // when
    Executable validatingDates = () -> reportDateValidator.validate(VALID_START_DATE,
                                                                    VALID_END_DATE);

    // then
    assertDoesNotThrow(validatingDates);
  }

  @Test
  public void givenNullStartDate_whenValidate_thenThrowInvalidReportDateException() {
    // given
    givenValidDinnerPeriod();

    // when
    Executable validatingDates = () -> reportDateValidator.validate(NULL_START_DATE,
                                                                    VALID_END_DATE);

    // then
    assertThrows(InvalidReportDateException.class, validatingDates);
  }

  @Test
  public void givenNullEndDate_whenValidate_thenThrowInvalidReportDateException() {
    // given
    givenValidDinnerPeriod();

    // when
    Executable validatingDates = () -> reportDateValidator.validate(VALID_START_DATE,
                                                                    NULL_END_DATE);

    // then
    assertThrows(InvalidReportDateException.class, validatingDates);
  }

  @Test
  public void givenStartBeingAEmptyString_whenValidate_thenDatesAreInvalid() {
    // given
    givenValidDinnerPeriod();

    // when
    Executable validatingDates = () -> reportDateValidator.validate(EMPTY_DATE, VALID_END_DATE);

    // then
    assertThrows(InvalidReportDateException.class, validatingDates);
  }

  @Test
  public void givenEndBeingAEmptyString_whenValidate_thenDatesAreInvalid() {
    // given
    givenValidDinnerPeriod();

    // when
    Executable validatingDates = () -> reportDateValidator.validate(VALID_START_DATE, EMPTY_DATE);

    // then
    assertThrows(InvalidReportDateException.class, validatingDates);
  }

  @Test
  public void givenADateBeingAWord_whenValidate_thenDatesAreInvalid() {
    // given
    givenValidDinnerPeriod();

    // when
    Executable validatingDates = () -> reportDateValidator.validate(A_WORD, VALID_END_DATE);

    // then
    assertThrows(InvalidReportDateException.class, validatingDates);
  }

  private void givenValidDinnerPeriod() {
    given(reportService.getHoppeningStart()).willReturn(VALID_START_DATE);
    given(reportService.getHoppeningEnd()).willReturn(VALID_END_DATE);
  }
}
