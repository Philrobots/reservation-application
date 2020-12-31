package ca.ulaval.glo4002.reservation.domain.reservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.reservation.ingredient.IngredientCalculator;

public class AllergiesDetector {

  private final IngredientCalculator ingredientCalculator;

  public AllergiesDetector(IngredientCalculator ingredientCalculator) {
    this.ingredientCalculator = ingredientCalculator;
  }

  public boolean isReservationAllergicFriendly(Reservation reservation,
                                               List<Reservation> existingReservations,
                                               Map<IngredientName, BigDecimal> dailyIngredients)
  {
    if (hasAllergicCustomer(reservation) && hasCustomerWhoCannotEatCarrots(reservation)) {
      return false;
    }

    if (hasAllergicCustomer(reservation)) {
      return !hasCarrotsInPreviousReservation(dailyIngredients);
    }

    if (hasCustomerWhoCannotEatCarrots(reservation)) {
      return !hasAllergicCustomerInPreviousReservation(reservation.getDinnerDate(), existingReservations);
    }
    return true;
  }

  private boolean hasCarrotsInPreviousReservation(Map<IngredientName, BigDecimal> ingredients) {
    return ingredients.containsKey(IngredientName.CARROTS);
  }

  private boolean hasCustomerWhoCannotEatCarrots(Reservation reservation) {
    Map<IngredientName, BigDecimal> ingredientQuantity = ingredientCalculator.getReservationIngredientsQuantity(reservation);
    return ingredientQuantity.containsKey(IngredientName.CARROTS);
  }

  private boolean hasAllergicCustomer(Reservation reservation) {
    return reservation.getRestrictionTypes().contains(RestrictionType.ALLERGIES);
  }

  private boolean hasAllergicCustomerInPreviousReservation(LocalDateTime dinnerDate, List<Reservation> existingReservations) {
    List<Reservation> reservations = existingReservations.stream()
                                                         .filter(reservation -> reservation.getDinnerDate()
                                                                                           .equals(dinnerDate))
                                                         .collect(Collectors.toList());
    return reservations.stream().anyMatch(reservation -> reservation.getRestrictionTypes().contains(RestrictionType.ALLERGIES));
  }
}
