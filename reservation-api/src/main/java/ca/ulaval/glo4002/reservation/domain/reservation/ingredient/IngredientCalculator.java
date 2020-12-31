package ca.ulaval.glo4002.reservation.domain.reservation.ingredient;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.reservation.Reservation;

import java.math.BigDecimal;
import java.util.Map;

public interface IngredientCalculator {
    Map<IngredientName, BigDecimal> getReservationIngredientsQuantity(Reservation reservation);
}
