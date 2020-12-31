package ca.ulaval.glo4002.reservation.domain.date;

import static com.google.common.truth.Truth.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DinerPeriodTest {
  private static final LocalDate A_START_DATE = LocalDate.of(2150, 7, 20);
  private static final LocalDate A_END_DATE = LocalDate.of(2150, 7, 30);
  private static final LocalDate A_DATE_BEFORE_START_DATE = LocalDate.of(2150, 2, 23);
  private static final LocalDate A_DATE_AFTER_END_DATE = LocalDate.of(2150, 9, 8);
  private static final LocalDate A_DATE_IN_PERIOD = LocalDate.of(2150, 7, 25);

  private DinerPeriod dinerPeriod;

  @BeforeEach
  public void setUp() {
    dinerPeriod = new DinerPeriod(A_START_DATE, A_END_DATE);
  }

  @Test
  public void givenADateInPeriod_whenIsWithinPeriod_thenDateIsValid() {
    // when
    Boolean isValid = dinerPeriod.isWithinPeriod(A_DATE_IN_PERIOD);

    // then
    assertThat(isValid).isTrue();
  }

  @Test
  public void givenADateBeforeStartDate_whenIsWithinPeriod_thenDateIsInvalid() {
    // when
    Boolean isValid = dinerPeriod.isWithinPeriod(A_DATE_BEFORE_START_DATE);

    // then
    assertThat(isValid).isFalse();
  }

  @Test
  public void givenADateAfterStartDate_whenIsWithinPeriod_thenDateIsInvalid() {
    // when
    Boolean isValid = dinerPeriod.isWithinPeriod(A_DATE_AFTER_END_DATE);

    // then
    assertThat(isValid).isFalse();
  }
}
