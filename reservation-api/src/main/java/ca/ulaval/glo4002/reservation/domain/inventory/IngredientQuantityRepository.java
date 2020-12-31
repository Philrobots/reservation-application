package ca.ulaval.glo4002.reservation.domain.inventory;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IngredientQuantityRepository {

    boolean isQuantityEmpty();

    void updateIngredientsQuantity(Map<IngredientName, BigDecimal> reservationIngredientsQuantity, LocalDate reservationDate);

    Map<IngredientName, BigDecimal> getIngredientsQuantityByDate(LocalDate date);

    Map<LocalDate, Map<IngredientName, BigDecimal>> getReportIngredientQuantity(List<LocalDate> dates);

    Map<LocalDate, Map<IngredientName, BigDecimal>> getAllIngredients();
}
