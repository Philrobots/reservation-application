package ca.ulaval.glo4002.reservation.domain.fullcourse.stock;

import java.time.LocalDate;

import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;

public interface Available {

  boolean isAvailable(LocalDate dinnerDate, LocalDate openingDay);

  IngredientName getIngredientName();
}
