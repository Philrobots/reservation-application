package ca.ulaval.glo4002.reservation.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.reservation.domain.chef.Selectable;
import ca.ulaval.glo4002.reservation.domain.exception.ForbiddenReservationException;
import ca.ulaval.glo4002.reservation.domain.hoppening.HoppeningConfigurationRequest;
import ca.ulaval.glo4002.reservation.domain.hoppening.HoppeningEvent;
import ca.ulaval.glo4002.reservation.domain.inventory.Inventory;
import ca.ulaval.glo4002.reservation.domain.material.Buffet;
import ca.ulaval.glo4002.reservation.domain.material.DailyDishesQuantity;
import ca.ulaval.glo4002.reservation.domain.report.ReportPeriod;
import ca.ulaval.glo4002.reservation.domain.chef.NoChefsAvailableException;
import ca.ulaval.glo4002.reservation.domain.reservation.*;
import ca.ulaval.glo4002.reservation.domain.reservation.vendorCode.ReservationNumber;
import ca.ulaval.glo4002.reservation.service.reservation.exception.TooManyPeopleException;

public class Restaurant {
  private static final int MAX_NUMBER_OF_CUSTOMERS_PER_DAY = 42;

  private final HoppeningEvent hoppeningEvent;
  private final ReservationFactory reservationFactory;
  private final ReservationBook reservationBook;
  private final Inventory ingredientInventory;
  private final Buffet buffet;
  private final Selectable chefSelector;
  private final ReservationRepository reservationRepository;

  public Restaurant(ReservationFactory reservationFactory,
                    ReservationBook reservationBook,
                    Inventory inventory,
                    HoppeningEvent hoppeningEvent,
                    Buffet buffet,
                    Selectable chefSelector, ReservationRepository reservationRepository)
  {
    this.reservationFactory = reservationFactory;
    this.reservationBook = reservationBook;
    this.ingredientInventory = inventory;
    this.hoppeningEvent = hoppeningEvent;
    this.buffet = buffet;
    this.chefSelector = chefSelector;
    this.reservationRepository = reservationRepository;
  }

  public BigDecimal getRestaurantIncome() {
    return reservationRepository.getTotalReservationIncome();
  }

  public ReservationNumber makeReservation(ReservationRequest reservationRequest) {
    Reservation reservation = reservationFactory.create(reservationRequest, hoppeningEvent);
    verifyReservation(reservation);
    try {
      hireChefsForNewReservation(reservation);
    } catch (NoChefsAvailableException noChefsAvailableException) {
      throw new ForbiddenReservationException();
    }
    buffet.updateDailyDishesQuantity(reservation);
    return registerReservation(reservation);
  }

  public Reservation getReservation(ReservationNumber reservationId) {
    return reservationBook.getReservation(reservationId);
  }

  public Map<LocalDate, DailyDishesQuantity> getDailyDishesQuantity(ReportPeriod reportPeriod) {
    return buffet.getDishesQuantityByDate(reportPeriod);
  }

  public HoppeningEvent getHoppeningEvent() {
    return hoppeningEvent;
  }

  public void configureHoppeningEvent(HoppeningConfigurationRequest hoppeningConfigurationRequest) {
    hoppeningEvent.configureHoppening(hoppeningConfigurationRequest);
  }

  private ReservationNumber registerReservation(Reservation reservation) {
    reservationBook.register(reservation);
    ingredientInventory.updateInventory(reservation);
    return reservation.getReservationId();
  }

  private void verifyMaximumNumberOfCustomersPerDay(Reservation reservation) {
    if (reservationBook.getNumberOfCustomersForADay(reservation.getDinnerDate())
        + reservation.getNumberOfCustomers() > MAX_NUMBER_OF_CUSTOMERS_PER_DAY)
    {
      throw new TooManyPeopleException();
    }
  }

  private void verifyAllergicConflict(Reservation reservation) {
    if (doesReservationCauseAllergicConflict(reservation)) {
      throw new ForbiddenReservationException();
    }
  }

  private void verifyIngredientAvailability(Reservation reservation) {
    if (!ingredientInventory.isInventoryStockAvailable(reservation, hoppeningEvent.getDinnerDinerPeriod().getStartDate()))
    {
      throw new ForbiddenReservationException();
    }
  }

  private void verifyReservation(Reservation reservation) {
    verifyAllergicConflict(reservation);
    verifyIngredientAvailability(reservation);
    verifyMaximumNumberOfCustomersPerDay(reservation);
  }

  private boolean doesReservationCauseAllergicConflict(Reservation reservation) {
    List<Reservation> existingReservationAtDinnerDate = reservationBook.getReservationsByDate(reservation.getDinnerDate());
    return ingredientInventory.doesReservationCauseAllergicConflict(reservation,
                                                                    existingReservationAtDinnerDate);
  }

  private void hireChefsForNewReservation(Reservation reservation) {
    List<Reservation> currentReservations = reservationBook.getReservationsByDate(reservation.getDinnerDate());
    currentReservations.add(reservation);
    chefSelector.hireChefsForReservations(getReservationsRestriction(currentReservations), getReservationDinnerDate(currentReservations));
  }

  private LocalDate getReservationDinnerDate(List<Reservation> currentReservations) {
    return currentReservations.get(0).getDinnerDate().toLocalDate();
  }

  private List<Map<RestrictionType, Integer>> getReservationsRestriction(List<Reservation> currentReservations) {
    return currentReservations.stream().map(Reservation::getRestrictionTypeCount).collect(Collectors.toList());
  }

  public Map<LocalDate, DailyDishesQuantity> getRestaurantTotalDishes() {
    return buffet.getAllDailyDishesQuantity();
  }

}
