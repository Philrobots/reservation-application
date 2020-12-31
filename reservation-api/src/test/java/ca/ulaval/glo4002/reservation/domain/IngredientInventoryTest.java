package ca.ulaval.glo4002.reservation.domain;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import ca.ulaval.glo4002.reservation.domain.fullcourse.stock.Available;
import ca.ulaval.glo4002.reservation.domain.inventory.IngredientInventory;
import ca.ulaval.glo4002.reservation.domain.reservation.ingredient.IngredientCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.reservation.AllergiesDetector;
import ca.ulaval.glo4002.reservation.domain.reservation.Reservation;
import ca.ulaval.glo4002.reservation.infra.inmemory.InMemoryIngredientQuantityRepository;

@ExtendWith(MockitoExtension.class)
public class IngredientInventoryTest {
  private static final LocalDateTime A_DINNER_DATE = LocalDateTime.of(2150, 7, 20, 3, 4);
  private static final IngredientName TOMATO = IngredientName.TOMATO;
  private static final LocalDate DATE_OUTSIDE_TOMATO_AVAILABILITY_PERIOD = LocalDate.of(2150, 7, 23);
  private static final LocalDate OPENING_DATE = LocalDate.of(2150, 7, 20);
  private static final BigDecimal QUANTITY = BigDecimal.valueOf(20);

  @Mock
  private InMemoryIngredientQuantityRepository inMemoryIngredientQuantityRepository;

  @Mock
  private AllergiesDetector allergiesDetector;

  @Mock
  private Reservation reservation;

  @Mock
  private List<Reservation> existingReservations;

  @Mock
  private Map<IngredientName, BigDecimal> dailyIngredients;

  @Mock
  private IngredientCalculator ingredientCalculator;

  @Mock
  private Available anIngredientStock;

  private IngredientInventory ingredientInventory;

  @BeforeEach
  public void setUpIngredientInventory() {
    Set<Available> ingredientsStocks = new HashSet<>();
    ingredientsStocks.add(anIngredientStock);
    ingredientInventory = new IngredientInventory(inMemoryIngredientQuantityRepository,
                                                  allergiesDetector, ingredientCalculator, ingredientsStocks);
  }


  @Test
  public void whenDoesReservationCauseAllergicConflict_thenDailyIngredientsAreFetched() {
    // given
    given(reservation.getDinnerDate()).willReturn(A_DINNER_DATE);

    // when
    ingredientInventory.doesReservationCauseAllergicConflict(reservation, existingReservations);

    // then
    verify(inMemoryIngredientQuantityRepository).getIngredientsQuantityByDate(A_DINNER_DATE.toLocalDate());
  }

  @Test
  public void whenDoesReservationCauseAllergicConflict_thenVerifyIfReservationIsAllergicFriendly() {
    // given
    given(reservation.getDinnerDate()).willReturn(A_DINNER_DATE);
    given(inMemoryIngredientQuantityRepository.getIngredientsQuantityByDate(A_DINNER_DATE.toLocalDate())).willReturn(dailyIngredients);

    // when
    ingredientInventory.doesReservationCauseAllergicConflict(reservation, existingReservations);

    // then
    verify(allergiesDetector).isReservationAllergicFriendly(reservation,
                                                            existingReservations,
                                                            dailyIngredients);
  }

  @Test
  public void whenGetIngredientsAtDate_thenIngredientsAtDateAreFetched() {
    // when
    ingredientInventory.getInventoryStockAtDate(A_DINNER_DATE.toLocalDate());

    // then
    verify(inMemoryIngredientQuantityRepository).getIngredientsQuantityByDate(A_DINNER_DATE.toLocalDate());
  }

  @Test
  public void whenUpdateIngredientInventory_thenIngredientQuantityRepositoryIsUpdated() {
    // when
    Map<IngredientName, BigDecimal> reservationIngredientsQuantity = Map.of(IngredientName.CHOCOLATE, BigDecimal.TEN);
    given(ingredientCalculator.getReservationIngredientsQuantity(reservation)).willReturn(reservationIngredientsQuantity);
    given(reservation.getDinnerDate()).willReturn(A_DINNER_DATE);
    ingredientInventory.updateInventory(reservation);


    // then
    verify(inMemoryIngredientQuantityRepository).updateIngredientsQuantity(reservationIngredientsQuantity, OPENING_DATE);
  }

  @Test
  public void givenReservationWithAvailable_whenAreIngredientAvailable_thenReservationIsAllowed() {
    // given
    given(ingredientCalculator.getReservationIngredientsQuantity(reservation)).willReturn(Collections.emptyMap());

    // when
    boolean isReservationAllowed = ingredientInventory.isInventoryStockAvailable(reservation, OPENING_DATE);

    // then
    assertThat(isReservationAllowed).isTrue();
  }

  @Test
  public void givenReservationWithUnavailableIngredient_whenAreIngredientAvailable_thenReservationIsNotAllowed() {
    // given
    given(reservation.getDinnerDate()).willReturn(DATE_OUTSIDE_TOMATO_AVAILABILITY_PERIOD.atStartOfDay());
    given(anIngredientStock.getIngredientName()).willReturn(TOMATO);
    given(anIngredientStock.isAvailable(reservation.getDinnerDate().toLocalDate(),
            OPENING_DATE)).willReturn(false);
    given(ingredientCalculator.getReservationIngredientsQuantity(reservation)).willReturn(Map.of(TOMATO,
            QUANTITY));

    // when
    boolean isReservationAllowed = ingredientInventory.isInventoryStockAvailable(reservation, OPENING_DATE);

    // then
    assertThat(isReservationAllowed).isFalse();
  }
}
