package ca.ulaval.glo4002.reservation.infra.inmemory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.inventory.IngredientQuantityRepository;

import ca.ulaval.glo4002.reservation.domain.util.MapUtil;

public class InMemoryIngredientQuantityRepository implements IngredientQuantityRepository {

  private final Map<LocalDate, Map<IngredientName, BigDecimal>> ingredientsQuantityPerDay = new HashMap<>();

  public boolean isQuantityEmpty() {
    return ingredientsQuantityPerDay.isEmpty();
  }

  public void updateIngredientsQuantity(Map<IngredientName, BigDecimal> reservationIngredientsQuantity, LocalDate reservationDate) {
    Map<IngredientName, BigDecimal> currentIngredientsQuantity = getIngredientsQuantityByDate(reservationDate);

    Map<IngredientName, BigDecimal> updatedIngredientsQuantity = MapUtil.merge(reservationIngredientsQuantity,
                                                                               currentIngredientsQuantity);
    ingredientsQuantityPerDay.put(reservationDate, updatedIngredientsQuantity);
  }

  public Map<IngredientName, BigDecimal> getIngredientsQuantityByDate(LocalDate date) {
    if (ingredientsQuantityPerDay.containsKey(date)) {
      return ingredientsQuantityPerDay.get(date);
    }
    ingredientsQuantityPerDay.put(date, new HashMap<>());
    return ingredientsQuantityPerDay.get(date);
  }

  public Map<LocalDate, Map<IngredientName, BigDecimal>> getReportIngredientQuantity(List<LocalDate> dates) {
    return  dates.stream().filter(ingredientsQuantityPerDay::containsKey).collect(Collectors.toMap(date -> date, ingredientsQuantityPerDay::get, (a, b) -> b));
  }

  public Map<LocalDate, Map<IngredientName, BigDecimal>> getAllIngredients() {
    return ingredientsQuantityPerDay;
  }
}
