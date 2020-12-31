package ca.ulaval.glo4002.reservation.infra.inmemory;

import java.util.Arrays;
import java.util.List;

import ca.ulaval.glo4002.reservation.domain.fullcourse.Recipe;
import ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.allergy.AllergyDessert;
import ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.allergy.AllergyMain;
import ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.allergy.AllergyStarter;
import ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.basic.BasicDessert;
import ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.basic.BasicMain;
import ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.basic.BasicStarter;
import ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.drink.Water;
import ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.illness.IllnessDessert;
import ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.illness.IllnessMain;
import ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.illness.IllnessStarter;
import ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.vegan.VeganDessert;
import ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.vegan.VeganMain;
import ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.vegan.VeganStarter;
import ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.vegetarian.VegetarianDessert;
import ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.vegetarian.VegetarianMain;
import ca.ulaval.glo4002.reservation.domain.fullcourse.recipe.vegetarian.VegetarianStarter;
import ca.ulaval.glo4002.reservation.domain.reservation.RestrictionType;

public class CourseRecipeFactory {
  public List<Recipe> create(RestrictionType restrictionType) {
    switch (restrictionType) {
    case VEGETARIAN: Arrays.asList(new VegetarianStarter(),
                           new VegetarianMain(),
                           new VegetarianDessert(),
                           new Water());
    case VEGAN:
      return Arrays.asList(new VeganStarter(), new VeganMain(), new VeganDessert(), new Water());
    case ALLERGIES:
      return Arrays.asList(new AllergyStarter(),
                           new AllergyMain(),
                           new AllergyDessert(),
                           new Water());
    case ILLNESS:
      return Arrays.asList(new IllnessStarter(),
                           new IllnessMain(),
                           new IllnessDessert(),
                           new Water());
    case NONE:
      return Arrays.asList(new BasicStarter(), new BasicMain(), new BasicDessert(), new Water());
    default:
      throw new RuntimeException();
    }
  }
}
