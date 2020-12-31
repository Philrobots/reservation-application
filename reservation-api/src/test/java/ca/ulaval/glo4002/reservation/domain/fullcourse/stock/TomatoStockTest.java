package ca.ulaval.glo4002.reservation.domain.fullcourse.stock;

import static com.google.common.truth.Truth.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;

@ExtendWith(MockitoExtension.class)
class TomatoStockTest {
  private static final int DAYS_BEFORE_TOMATOES_ARE_AVAILABLE = 5;
  private static final LocalDate OPENING_DAY = LocalDate.of(2150, 7, 20);
  private static final LocalDate DATE_BEFORE_TOMATOES_AVAILABILITY = LocalDate.of(2150, 7, 23);
  private static final LocalDate DATE_AFTER_TOMATOES_AVAILABILITY = LocalDate.of(2150, 7, 26);
  private static final LocalDate DATE_TOMATOES_ARE_AVAILABLE = LocalDate.of(2150, 7, 25);

  private TomatoStock tomatoStock;

  @BeforeEach
  public void setUp() {
    tomatoStock = new TomatoStock(IngredientName.TOMATO, DAYS_BEFORE_TOMATOES_ARE_AVAILABLE);
  }

  @Test
  public void givenADateBeforeTomatoAvailability_whenIsAvailable_thenShouldNotBeAvailable() {
    // when
    boolean isTomatoAvailable = tomatoStock.isAvailable(DATE_BEFORE_TOMATOES_AVAILABILITY,
                                                        OPENING_DAY);

    // then
    assertThat(isTomatoAvailable).isFalse();
  }

  @Test
  public void givenReservationWhenIngredientIsAvailable_whenIsAvailable_thenShouldBeAvailable() {
    // when
    boolean isTomatoAvailable = tomatoStock.isAvailable(DATE_AFTER_TOMATOES_AVAILABILITY,
                                                        OPENING_DAY);

    // then
    assertThat(isTomatoAvailable).isTrue();
  }

  @Test
  public void givenReservationMadeTheDayTomatoesAreAvailable_whenIsAvailable_thenShouldBeAvailable() {
    // when
    boolean isTomatoAvailable = tomatoStock.isAvailable(DATE_TOMATOES_ARE_AVAILABLE, OPENING_DAY);

    // then
    assertThat(isTomatoAvailable).isTrue();
  }
}
