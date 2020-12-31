package ca.ulaval.glo4002.reservation.domain.report.chef;

import static com.google.common.truth.Truth.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.domain.chef.Chef;
import ca.ulaval.glo4002.reservation.domain.chef.ChefPriority;
import ca.ulaval.glo4002.reservation.domain.reservation.RestrictionType;

@ExtendWith(MockitoExtension.class)
public class ChefReportGeneratorTest {

  private static final ChefPriority A_CHEF_TYPE = ChefPriority.SECOND;
  private static final String A_CHEF_NAME = "Bob Smarties";
  private static final Set<RestrictionType> SOME_SPECIALTIES = Set.of(RestrictionType.VEGAN);

  private static final ChefPriority ANOTHER_CHEF_TYPE = ChefPriority.NINTH;
  private static final String ANOTHER_CHEF_NAME = "Amélie Mélo";
  private static final Set<RestrictionType> SOME_OTHER_SPECIALTIES = Set.of(RestrictionType.ALLERGIES,
                                                                            RestrictionType.VEGAN);

  private static final LocalDate A_DATE = LocalDate.of(Integer.parseInt("2020"),
                                                       Integer.parseInt("12"),
                                                       Integer.parseInt("10"));
  private static final BigDecimal EXPECTED_PRICE_FOR_TWO_CHEF = BigDecimal.valueOf(12000);
  private static final BigDecimal EXPECTED_PRICE_FOR_NO_CHEF = BigDecimal.ZERO;

  private ChefReportGenerator chefReportGenerator;
  private Chef aChef;
  private Chef anotherChef;

  @BeforeEach
  public void setUpChefGenerator() {
    chefReportGenerator = new ChefReportGenerator();
    aChef = new Chef(A_CHEF_NAME, A_CHEF_TYPE, SOME_SPECIALTIES);
    anotherChef = new Chef(ANOTHER_CHEF_NAME, ANOTHER_CHEF_TYPE, SOME_OTHER_SPECIALTIES);
  }

  @Test
  public void givenTwoChefs_whenGenerateReport_thenTheReportShouldHavePriceOfTwoChef() {
    // given
    Map<LocalDate, Set<Chef>> chefsByDay = givenADayWithTwoChef();

    // when
    ChefReport chefReport = chefReportGenerator.generateReport(chefsByDay);

    BigDecimal actualPrice = chefReport.getChefReportInformations().get(0).getTotalPrice();
    assertThat(actualPrice).isEqualTo(EXPECTED_PRICE_FOR_TWO_CHEF);
  }

  @Test
  public void givenNoChef_whenGenerateReport_thenReportShouldHavePriceZero() {
    // given
    Map<LocalDate, Set<Chef>> chefsByDay = givenADayOfNoChef();

    // when
    ChefReport chefReport = chefReportGenerator.generateReport(chefsByDay);

    // then
    BigDecimal actualPrice = chefReport.getChefReportInformations().get(0).getTotalPrice();
    assertThat(actualPrice).isEqualTo(EXPECTED_PRICE_FOR_NO_CHEF);
  }

  @Test
  public void givenTwoChefs_whenGenerateReport_thenReportShouldHaveTwoChefs() {
    // given
    Map<LocalDate, Set<Chef>> chefsByDay = givenADayWithTwoChef();
    Set<Chef> expectedChefs = Set.of(aChef, anotherChef);

    // when
    ChefReport chefReport = chefReportGenerator.generateReport(chefsByDay);

    // then
    assertThat(chefReport.getChefReportInformations().get(0).getChefs()).isEqualTo(expectedChefs);
  }

  private Map<LocalDate, Set<Chef>> givenADayWithTwoChef() {
    Map<LocalDate, Set<Chef>> restaurantChefs = new HashMap<>();
    Set<Chef> chefs = Set.of(aChef, anotherChef);
    restaurantChefs.put(A_DATE, chefs);
    return restaurantChefs;
  }

  private Map<LocalDate, Set<Chef>> givenADayOfNoChef() {
    Map<LocalDate, Set<Chef>> restaurantChefs = new HashMap<>();
    restaurantChefs.put(A_DATE, Collections.emptySet());
    return restaurantChefs;
  }
}
