package ca.ulaval.glo4002.reservation.domain.inventory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.fullcourse.stock.Available;
import ca.ulaval.glo4002.reservation.domain.reservation.AllergiesDetector;
import ca.ulaval.glo4002.reservation.domain.reservation.Reservation;
import ca.ulaval.glo4002.reservation.domain.reservation.ingredient.IngredientCalculator;
import ca.ulaval.glo4002.reservation.infra.inmemory.InMemoryIngredientQuantityRepository;

public class IngredientInventory implements Inventory {

  private final IngredientQuantityRepository ingredientQuantityRepository;
  private final AllergiesDetector allergiesDetector;
  private final IngredientCalculator ingredientCalculator;
  private final Set<Available> ingredientsStock;

  public IngredientInventory(InMemoryIngredientQuantityRepository ingredientQuantityRepository,
                             AllergiesDetector allergiesDetector,
                             IngredientCalculator ingredientCalculator,
                             Set<Available> ingredientsStock)
  {
    this.ingredientQuantityRepository = ingredientQuantityRepository;
    this.allergiesDetector = allergiesDetector;
    this.ingredientCalculator = ingredientCalculator;
    this.ingredientsStock = ingredientsStock;
  }

  public void updateInventory(Reservation reservation) {
    LocalDate reservationDate = reservation.getDinnerDate().toLocalDate();
    Map<IngredientName, BigDecimal> reservationIngredientsQuantity = ingredientCalculator.getReservationIngredientsQuantity(reservation);
    ingredientQuantityRepository.updateIngredientsQuantity(reservationIngredientsQuantity, reservationDate);
  }

  public boolean doesReservationCauseAllergicConflict(Reservation reservation, List<Reservation> existingReservations) {
    Map<IngredientName, BigDecimal> dailyIngredients = getInventoryStockAtDate(reservation.getDinnerDate().toLocalDate());
    return !allergiesDetector.isReservationAllergicFriendly(reservation, existingReservations, dailyIngredients);
  }

  public boolean isInventoryStockAvailable(Reservation reservation, LocalDate restaurantOpeningDate) {
    return ingredientsStock.stream().noneMatch(ingredientStock -> doesReservationContainIngredient(reservation, ingredientStock.getIngredientName())
            && !(ingredientStock.isAvailable(reservation.getDinnerDate().toLocalDate(),
            restaurantOpeningDate)));
  }

  public Map<IngredientName, BigDecimal> getInventoryStockAtDate(LocalDate date) {
    return ingredientQuantityRepository.getIngredientsQuantityByDate(date);
  }

  private boolean doesReservationContainIngredient(Reservation reservation, IngredientName ingredientName)
  {
    Map<IngredientName, BigDecimal> ingredientQuantity = ingredientCalculator.getReservationIngredientsQuantity(reservation);
    return ingredientQuantity.containsKey(ingredientName);
  }
}
