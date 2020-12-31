package ca.ulaval.glo4002.reservation.domain.report.chef;

import static com.google.common.truth.Truth.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.reservation.domain.chef.Chef;
import ca.ulaval.glo4002.reservation.domain.chef.ChefPriority;
import ca.ulaval.glo4002.reservation.domain.reservation.RestrictionType;

public class ChefIngredientReportTest {

  private static final ChefPriority A_CHEF_TYPE = ChefPriority.SECOND;
  private static final String A_CHEF_NAME = "Bob Smarties";
  private static final Set<RestrictionType> SOME_SPECIALTIES = Set.of(RestrictionType.VEGAN);
  private static final String A_DATE = "2020-10-23";
  private static final BigDecimal A_PRICE = BigDecimal.TEN;

  private ChefReport chefReport;

  @BeforeEach
  public void setUpChefReport() {
    chefReport = new ChefReport();
  }

  @Test
  public void givenNoChefReportInformation_whenGetChefReportInformation_thenChefReportInformationShouldBeEmpty() {
    // when
    List<ChefReportInformation> chefReportInformation = chefReport.getChefReportInformations();

    // then
    assertThat(chefReportInformation).isEmpty();
  }

  @Test
  public void givenAChefReportInformation_whenAddChefReportInformation_thenChefReportContainsNewChefs() {
    // given
    Set<Chef> chefs = Set.of(new Chef(A_CHEF_NAME, A_CHEF_TYPE, SOME_SPECIALTIES));
    ChefReportInformation chefReportInformation = new ChefReportInformation(chefs, A_DATE, A_PRICE);
    chefReport.addChefReportInformation(A_DATE, chefs, A_PRICE);

    // when
    List<ChefReportInformation> multipleChefReportInformation = chefReport.getChefReportInformations();

    // then
    assertThat(multipleChefReportInformation.get(0)
                                            .getChefs()).isEqualTo(chefReportInformation.getChefs());
  }
}
