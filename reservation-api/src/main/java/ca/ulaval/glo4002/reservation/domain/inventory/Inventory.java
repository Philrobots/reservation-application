package ca.ulaval.glo4002.reservation.domain.inventory;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.reservation.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface Inventory {

    void updateInventory(Reservation reservation);

    boolean doesReservationCauseAllergicConflict(Reservation reservation,
                                                 List<Reservation> existingReservations);

    boolean isInventoryStockAvailable(Reservation reservation,
                                      LocalDate restaurantOpeningDate);

    Map<IngredientName, BigDecimal> getInventoryStockAtDate(LocalDate date);

}
