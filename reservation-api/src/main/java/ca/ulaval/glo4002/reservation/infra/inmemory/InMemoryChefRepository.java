package ca.ulaval.glo4002.reservation.infra.inmemory;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ca.ulaval.glo4002.reservation.domain.chef.Chef;
import ca.ulaval.glo4002.reservation.domain.chef.ChefPriority;
import ca.ulaval.glo4002.reservation.domain.report.chef.ChefRepository;
import ca.ulaval.glo4002.reservation.domain.reservation.RestrictionType;

public class InMemoryChefRepository implements ChefRepository {

  private final Map<LocalDate, Set<Chef>> restaurantChefsSchedule;
  private Set<Chef> availableChefs;

  public InMemoryChefRepository() {
    this.restaurantChefsSchedule = new HashMap<>();
    generateBaseChefs();
  }

  private void generateBaseChefs() {
    // celle la je ne la comprends pas pour vrai, on avait fait une belle énum qui contient toute les chefs pis il a quelqu'un qui l'a changé quelques heures avant la
    // remise pour mettre ca dans une passe de refactor
    availableChefs = new HashSet<>();
    availableChefs.add(new Chef("Thierry Aki", ChefPriority.FIRST, Set.of(RestrictionType.NONE)));
    availableChefs.add(new Chef("Bob Smarties",
                                ChefPriority.SECOND,
                                Set.of(RestrictionType.VEGAN)));
    availableChefs.add(new Chef("Bob Rossbeef",
                                ChefPriority.THIRD,
                                Set.of(RestrictionType.VEGETARIAN)));
    availableChefs.add(new Chef("Bill Adicion",
                                ChefPriority.FOURTH,
                                Set.of(RestrictionType.ALLERGIES)));
    availableChefs.add(new Chef("Omar Calmar",
                                ChefPriority.FIFTH,
                                Set.of(RestrictionType.ILLNESS)));
    availableChefs.add(new Chef("Écharlotte Cardin",
                                ChefPriority.SIXTH,
                                Set.of(RestrictionType.VEGAN, RestrictionType.ALLERGIES)));
    availableChefs.add(new Chef("Éric Ardo",
                                ChefPriority.SEVENTH,
                                Set.of(RestrictionType.VEGETARIAN, RestrictionType.ILLNESS)));
    availableChefs.add(new Chef("Hans Riz",
                                ChefPriority.EIGHTH,
                                Set.of(RestrictionType.NONE, RestrictionType.ILLNESS)));
    availableChefs.add(new Chef("Amélie Mélo",
                                ChefPriority.NINTH,
                                Set.of(RestrictionType.ALLERGIES, RestrictionType.VEGAN)));
  }

  public void updateRestaurantChefs(LocalDate dinnerDate, Set<Chef> newChefs) {
    restaurantChefsSchedule.put(dinnerDate, newChefs);
  }

  public Map<LocalDate, Set<Chef>> getAllChefsWorkSchedule() {
    return restaurantChefsSchedule;
  }

  public Set<Chef> getAllChefs() {
    return availableChefs;
  }
}
