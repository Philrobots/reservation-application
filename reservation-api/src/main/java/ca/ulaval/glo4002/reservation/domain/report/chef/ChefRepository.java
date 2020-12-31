package ca.ulaval.glo4002.reservation.domain.report.chef;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import ca.ulaval.glo4002.reservation.domain.chef.Chef;

public interface ChefRepository {

  void updateRestaurantChefs(LocalDate dinnerDate, Set<Chef> newChefs);

  Map<LocalDate, Set<Chef>> getAllChefsWorkSchedule();

  Set<Chef> getAllChefs();
}
