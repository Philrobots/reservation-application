package ca.ulaval.glo4002.reservation.domain;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ulaval.glo4002.reservation.domain.chef.Selectable;
import ca.ulaval.glo4002.reservation.domain.inventory.IngredientInventory;
import ca.ulaval.glo4002.reservation.domain.reservation.*;
import ca.ulaval.glo4002.reservation.domain.reservation.vendorCode.ReservationNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.date.DinerPeriod;
import ca.ulaval.glo4002.reservation.domain.exception.ForbiddenReservationException;
import ca.ulaval.glo4002.reservation.domain.hoppening.HoppeningConfigurationRequest;
import ca.ulaval.glo4002.reservation.domain.hoppening.HoppeningEvent;
import ca.ulaval.glo4002.reservation.domain.material.Buffet;
import ca.ulaval.glo4002.reservation.domain.material.DailyDishesQuantity;
import ca.ulaval.glo4002.reservation.domain.report.ReportPeriod;

@ExtendWith(MockitoExtension.class)
public class RestaurantTest {
  private static final boolean CAUSE_ALLERGIC_CONFLICT = true;
  private static final boolean DOES_NOT_CAUSE_ALLERGIC_CONFLICT = false;
  private static final boolean NOT_ALL_INGREDIENTS_AVAILABLE = false;
  private static final LocalDateTime A_DATE = LocalDateTime.of(2020, 7, 22, 23, 23);
  private static final int FORTY_ONE_CUSTOMERS = 41;
  private static final int ONE_CUSTOMER = 1;
  private static final LocalDate AN_OPENING_DATE = LocalDate.of(2020, 7, 20);

  @Mock
  private ReservationFactory reservationFactory;

  @Mock
  private ReservationRequest reservationRequest;

  @Mock
  private ReservationBook reservationBook;

  @Mock
  private IngredientInventory ingredientInventory;

  @Mock
  private Reservation aReservation;

  @Mock
  private ReservationNumber expectedReservationId;

  @Mock
  private ReservationNumber aReservationId;

  @Mock
  private HoppeningEvent hoppeningEvent;

  @Mock
  private HoppeningConfigurationRequest hoppeningConfigurationRequest;

  @Mock
  private Buffet buffet;

  @Mock
  private ReportPeriod reportPeriod;

  @Mock
  private DinerPeriod dinnerDinerPeriod;

  @Mock
  private Map<LocalDate, DailyDishesQuantity> dailyDishesQuantity;

  @Mock
  private Selectable chefSelector;

  @Mock
  private ReservationRepository reservationRepository;

  private List<Reservation> reservations;

  private Restaurant restaurant;

  @BeforeEach
  public void setUpRestaurant() {
    restaurant = new Restaurant(reservationFactory, reservationBook, ingredientInventory, hoppeningEvent, buffet, chefSelector, reservationRepository);
    reservations = new ArrayList<>();
  }

  @Test
  public void whenMakeReservation_thenReservationIsCreated() {
    // given
    givenValidReservationRequest();


    // when
    restaurant.makeReservation(reservationRequest);

    // then
    verify(reservationFactory).create(reservationRequest, hoppeningEvent);
  }

  @Test
  public void whenMakeReservation_thenReservationIsRegisteredInReservationBook() {
    // given
    givenValidReservationRequest();

    // when
    restaurant.makeReservation(reservationRequest);

    // then
    verify(reservationBook).register(aReservation);
  }

  @Test
  public void givenReservationCausingAnAllergicConflict_whenMakeReservation_thenReservationIsNotRegistered() {
    // given
    givenReservationRequestCausingAllergicConflict();

    // when
    try {
      restaurant.makeReservation(reservationRequest);
    } catch (Exception ignored) {
    }

    // then
    verify(reservationBook, times(0)).register(aReservation);
  }

  @Test
  public void givenReservationNotCausingAllergicConflict_whenMakeReservation_thenReservationIsRegistered() {
    // given
    givenValidReservationRequest();

    // when
    restaurant.makeReservation(reservationRequest);

    // then
    verify(reservationBook).register(aReservation);
  }

  @Test
  public void whenMakeReservation_thenReservationIdOfTheCreatedReservationIsReturned() {
    // given
    givenValidReservationRequest();
    given(aReservation.getReservationId()).willReturn(expectedReservationId);

    // when
    ReservationNumber reservationId = restaurant.makeReservation(reservationRequest);

    // then
    assertThat(reservationId).isEqualTo(expectedReservationId);
  }

  @Test
  public void givenReservationCausingAllergicConflict_whenMakeReservation_thenThrowForbiddenReservationException() {
    // given
    givenReservationRequestCausingAllergicConflict();

    // when
    Executable makingReservation = () -> restaurant.makeReservation(reservationRequest);

    // then
    assertThrows(ForbiddenReservationException.class, makingReservation);
  }

  @Test
  public void givenNotAllIngredientsAvailableForReservation_whenMakeReservation_thenReservationIsRegistered() {
    // given
    givenNotAllIngredientsAreAvailable();

    // when
    Executable makingReservation = () -> restaurant.makeReservation(reservationRequest);

    // then
    assertThrows(ForbiddenReservationException.class, makingReservation);
  }

  @Test
  public void givenAnExistingReservation_whenGetReservationById_thenTheReservationIsRetrievedFromTheReservationBook() {
    // given
    given(reservationBook.getReservation(aReservationId)).willReturn(aReservation);

    // when
    Reservation reservation = restaurant.getReservation(aReservationId);

    // then
    assertThat(reservation).isEqualTo(aReservation);
  }

  @Test
  public void givenAReservationThatExceedMaximumCapacityOfCustomerForADay_whenMakeReservation_thenReservationIsNotRegistered() {
    try {
      restaurant.makeReservation(reservationRequest);
    } catch (Exception ignored) {
    }

    // then
    verify(reservationBook, times(0)).register(aReservation);
  }

  @Test
  public void givenAReservationThatDoesNotExceedMaxNumberOfCustomersForADay_whenMakeReservation_thenReservationIsRegistered() {
    // given
    given(aReservation.getNumberOfCustomers()).willReturn(ONE_CUSTOMER);
    given(reservationBook.getNumberOfCustomersForADay(A_DATE)).willReturn(FORTY_ONE_CUSTOMERS);
    givenValidReservationRequest();

    // when
    restaurant.makeReservation(reservationRequest);

    // then
    verify(reservationBook).register(aReservation);
  }

  @Test
  public void whenGetDailyDishesQuantity_thenGetDailyDishedQuantitiesFromBuffetIsCalled() {
    // given
    given(buffet.getDishesQuantityByDate(reportPeriod)).willReturn(dailyDishesQuantity);

    // when
    restaurant.getDailyDishesQuantity(reportPeriod);

    // then
    verify(buffet).getDishesQuantityByDate(reportPeriod);
  }

  @Test
  public void whenConfigureHoppeningEvent_thenHoppeningEventIsUpdated() {
    // when
    restaurant.configureHoppeningEvent(hoppeningConfigurationRequest);

    // then
    verify(hoppeningEvent).configureHoppening(hoppeningConfigurationRequest);
  }

  @Test
  public void givenAReservation_whenMakeReservation_thenNecessaryDishesAmountIsUpdated() {
    // given
    givenValidReservationRequest();

    // when
    restaurant.makeReservation(reservationRequest);

    // then
    verify(buffet).updateDailyDishesQuantity(aReservation);
  }

  @Test
  public void whenMakeReservation_thenChefsAreHired() {
    // given
    givenValidReservationRequest();

    // when
    restaurant.makeReservation(reservationRequest);

    // then
    verify(chefSelector).hireChefsForReservations(givenListOfTwoRestrictionType(), A_DATE.toLocalDate());
  }

  @Test
  public void whenMakeReservation_thenVerifyIfThereIsAConflictCausedByAllergies() {
    // given
    givenValidReservationRequest();

    // when
    restaurant.makeReservation(reservationRequest);

    // then
    verify(ingredientInventory).doesReservationCauseAllergicConflict(aReservation, reservations);
  }

  private void givenValidReservationRequest() {
    given(aReservation.getRestrictionTypeCount()).willReturn(givenAReservationRestrictionType());
    reservations.add(aReservation);
    given(dinnerDinerPeriod.getStartDate()).willReturn(AN_OPENING_DATE);
    given(hoppeningEvent.getDinnerDinerPeriod()).willReturn(dinnerDinerPeriod);
    given(ingredientInventory.isInventoryStockAvailable(aReservation,
            AN_OPENING_DATE)).willReturn(true);
    given(aReservation.getDinnerDate()).willReturn(A_DATE);
    given(reservationFactory.create(reservationRequest, hoppeningEvent)).willReturn(aReservation);
    given(reservationBook.getReservationsByDate(aReservation.getDinnerDate())).willReturn(reservations);
  }

  private Map<RestrictionType, Integer> givenAReservationRestrictionType() {
    Map<RestrictionType, Integer> restrictionTypeIntegerMap = new HashMap<>();
    restrictionTypeIntegerMap.put(RestrictionType.ALLERGIES, 4);
    return restrictionTypeIntegerMap;
  }

  private List<Map<RestrictionType, Integer>> givenListOfTwoRestrictionType() {
    List<Map<RestrictionType, Integer>> restrictionTypeIntegerMap = new ArrayList<>();
    restrictionTypeIntegerMap.add(givenAReservationRestrictionType());
    restrictionTypeIntegerMap.add(givenAReservationRestrictionType());
    return restrictionTypeIntegerMap;
  }

  private void givenReservationRequestCausingAllergicConflict() {
    given(reservationBook.getReservationsByDate(any())).willReturn(reservations);
    given(reservationFactory.create(reservationRequest, hoppeningEvent)).willReturn(aReservation);
    given(ingredientInventory.doesReservationCauseAllergicConflict(aReservation,
                                                                   reservations)).willReturn(CAUSE_ALLERGIC_CONFLICT);
  }

  private void givenNotAllIngredientsAreAvailable() {
    given(dinnerDinerPeriod.getStartDate()).willReturn(AN_OPENING_DATE);
    given(hoppeningEvent.getDinnerDinerPeriod()).willReturn(dinnerDinerPeriod);
    given(reservationBook.getReservationsByDate(any())).willReturn(reservations);
    given(reservationFactory.create(reservationRequest, hoppeningEvent)).willReturn(aReservation);
    given(ingredientInventory.doesReservationCauseAllergicConflict(aReservation,
                                                                   reservations)).willReturn(DOES_NOT_CAUSE_ALLERGIC_CONFLICT);
    given(ingredientInventory.isInventoryStockAvailable(aReservation,
                                                                  AN_OPENING_DATE)).willReturn(NOT_ALL_INGREDIENTS_AVAILABLE);
  }
}
