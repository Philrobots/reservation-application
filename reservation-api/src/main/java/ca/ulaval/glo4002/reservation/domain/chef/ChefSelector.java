package ca.ulaval.glo4002.reservation.domain.chef;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import ca.ulaval.glo4002.reservation.domain.report.chef.ChefRepository;
import ca.ulaval.glo4002.reservation.domain.reservation.RestrictionType;

public class ChefSelector implements Selectable {

  private final ChefRepository chefRepository;
  private final List<Chef> availableChefs;

  public ChefSelector(ChefRepository chefRepository) {
    this.chefRepository = chefRepository;
    this.availableChefs = new ArrayList<>(chefRepository.getAllChefs());
  }

  public void hireChefsForReservations(List<Map<RestrictionType, Integer>> reservationsRestriction, LocalDate dinnerDate) {
    resetChefCustomers();
    Map<RestrictionType, Integer> dishQuantities = new HashMap<>();
    reservationsRestriction.forEach(restrictionType -> mergeMaps(dishQuantities, restrictionType));

    Set<Chef> suitableChefs = getSuitableChefs(dishQuantities);
    List<List<Chef>> suitableChefPermutations = generatePermutation(new ArrayList<>(suitableChefs));
    Set<Set<Chef>> possibleChefCombinations = findPossibleChefCombinations(dishQuantities,
                                                                           suitableChefPermutations);
    if (possibleChefCombinations.isEmpty()) {
      throw new NoChefsAvailableException();
    }

    Set<Set<Chef>> optimalChefCombinationsBasedOnPrice = findOptimalChefCombinationsBasedOnPrice(possibleChefCombinations);
    Set<Chef> hiredChefs = findOptimalChefCombinationBasedOnPriority(optimalChefCombinationsBasedOnPrice);

    updateChefCustomers(dishQuantities, hiredChefs);
    chefRepository.updateRestaurantChefs(dinnerDate, hiredChefs);
  }

  private Set<Chef> getSuitableChefs(Map<RestrictionType, Integer> dishQuantities) {
    Set<Chef> suitableChefs = new HashSet<>();
    for (RestrictionType restrictionType : dishQuantities.keySet()) {
      for (Chef chef : availableChefs) {
        if (chef.getSpecialties().contains(restrictionType) && chef.getAvailableCustomers() > 0) {
          suitableChefs.add(chef);
        }
      }
    }
    return suitableChefs;
  }

  private void resetChefCustomers() {
    availableChefs.forEach(Chef::resetNumberOfCustomers);
  }

  private <T> void mergeMaps(Map<T, Integer> firstMap, Map<T, Integer> secondMap) {
    secondMap.forEach((key, value) -> {
      if (firstMap.containsKey(key)) {
        firstMap.put(key, firstMap.get(key) + value);
      } else {
        firstMap.put(key, value);
      }
    });
  }

  // https://stackoverflow.com/a/10305419
  private List<List<Chef>> generatePermutation(List<Chef> original) {
    if (original.isEmpty()) {
      return generateEmptyChefList();
    }
    Chef firstElement = original.remove(0);
    List<List<Chef>> returnValue = new ArrayList<>();
    List<List<Chef>> permutations = generatePermutation(original);
    for (List<Chef> smallerPermutated : permutations) {
      for (int index = 0; index <= smallerPermutated.size(); index++) {
        List<Chef> temp = new ArrayList<>(smallerPermutated);
        temp.add(index, firstElement);
        returnValue.add(temp);
      }
    }
    return returnValue;
  }

  private Set<Set<Chef>> findPossibleChefCombinations(Map<RestrictionType, Integer> dishQuantities,
                                                      List<List<Chef>> suitableChefPermutations)
  {
    Set<Set<Chef>> possibleChefCombinations = new HashSet<>();

    for (List<Chef> chefPermutation : suitableChefPermutations) {
      Set<Chef> possibleChefCombination = new HashSet<>();
      Map<RestrictionType, Integer> dishesNeededQuantities = new HashMap<>(dishQuantities);
      for (Chef chef : chefPermutation) {

        if (chefShouldBeHired(dishesNeededQuantities, chef)) {
          updateDishesNeededQuantities(dishesNeededQuantities, chef);
          possibleChefCombination.add(chef);
        }

        if (allCustomersSatisfied(dishesNeededQuantities)) {
          possibleChefCombinations.add(possibleChefCombination);
          break;
        }
      }
    }
    return possibleChefCombinations;
  }

  private boolean chefShouldBeHired(Map<RestrictionType, Integer> dishesNeededCount, Chef chef) {
    return dishesNeededCount.keySet().stream().anyMatch(restrictionType -> chef.getSpecialties().contains(restrictionType)
                                                         && dishesNeededCount.get(restrictionType) >= 0);
  }

  private boolean allCustomersSatisfied(Map<RestrictionType, Integer> restrictionTypeCount) {
    return restrictionTypeCount.values().stream().noneMatch(count -> count > 0);
  }

  private List<List<Chef>> generateEmptyChefList() {
    List<List<Chef>> result = new ArrayList<>();
    result.add(new ArrayList<>());
    return result;
  }

  private Set<Set<Chef>> findOptimalChefCombinationsBasedOnPrice(Set<Set<Chef>> possibleChefCombinations) {
    BigDecimal minimumPrice = BigDecimal.valueOf(Double.MAX_VALUE);
    Set<Set<Chef>> bestCombinations = new HashSet<>();
    for (Set<Chef> chefCombination : possibleChefCombinations) {
      BigDecimal totalChefsPrice = BigDecimal.ZERO;
      for (Chef chef : chefCombination) {
        totalChefsPrice = totalChefsPrice.add(chef.getPrice());
      }
      if (minimumPrice.compareTo(totalChefsPrice) == 0) {
        bestCombinations.add(chefCombination);
      } else if (minimumPrice.compareTo(totalChefsPrice) > 0) {
        bestCombinations.clear();
        minimumPrice = totalChefsPrice;
        bestCombinations.add(chefCombination);
      }
    }
    return bestCombinations;
  }

  private Set<Chef> findOptimalChefCombinationBasedOnPriority(Set<Set<Chef>> optimalChefCombinationsBasedOnPrice) {
    int maximumPriority = 0;
    Set<Chef> bestCombination = new HashSet<>();
    for (Set<Chef> chefCombination : optimalChefCombinationsBasedOnPrice) {
      int totalChefsPriority = 0;
      for (Chef chef : chefCombination) {
        totalChefsPriority += chef.getPriority().getPriorityValue();
      }
      if (totalChefsPriority > maximumPriority) {
        maximumPriority = totalChefsPriority;
        bestCombination = chefCombination;
      }
    }
    return bestCombination;
  }

  private void updateDishesNeededQuantities(Map<RestrictionType, Integer> dishesNeededQuantities,
                                            Chef chef)
  {
    int chefCapacity = chef.getAvailableCustomers();
    for (RestrictionType restrictionType : chef.getSpecialties()) {
      if (!dishesNeededQuantities.containsKey(restrictionType)) {
        continue;
      }

      int dishesNeeded = dishesNeededQuantities.get(restrictionType);
      if (dishesNeeded > 0) {
        dishesNeededQuantities.put(restrictionType, dishesNeeded - chefCapacity);
        chefCapacity -= dishesNeeded;
      }
      if (chefCapacity <= 0) {
        break;
      }
    }
  }

  private void updateChefCustomers(Map<RestrictionType, Integer> dishesNeededCount,
                                   Set<Chef> hiredChefs)
  {
    for (Chef chef : hiredChefs) {
      for (RestrictionType restrictionType : chef.getSpecialties()) {
        if (dishesNeededCount.containsKey(restrictionType)) {
          int numberOfDishesNeeded = dishesNeededCount.get(restrictionType);
          dishesNeededCount.put(restrictionType,
                                numberOfDishesNeeded - chef.getAvailableCustomers());

          chef.addCustomers(Math.min(numberOfDishesNeeded, chef.getAvailableCustomers()));
        }
      }
    }
  }
}
