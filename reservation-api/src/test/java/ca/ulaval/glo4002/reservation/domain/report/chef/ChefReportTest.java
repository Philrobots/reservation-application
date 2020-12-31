package ca.ulaval.glo4002.reservation.domain.report.chef;

import static com.google.common.truth.Truth.assertThat;

import java.math.BigDecimal;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.reservation.domain.chef.Chef;
import ca.ulaval.glo4002.reservation.domain.chef.ChefPriority;
import ca.ulaval.glo4002.reservation.domain.reservation.RestrictionType;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChefReportTest {

  private static final ChefPriority A_CHEF_TYPE = ChefPriority.FIRST;
  private static final String A_CHEF_NAME = "Thierry Aki";
  private static final Set<RestrictionType> SOME_SPECIALTIES = Set.of(RestrictionType.NONE);
  private static final Chef A_CHEF = new Chef(A_CHEF_NAME, A_CHEF_TYPE, SOME_SPECIALTIES);
  private static final Set<Chef> SOME_CHEFS = Set.of(A_CHEF);
  private static final BigDecimal A_TOTAL_PRICE = BigDecimal.valueOf(1205);
  private static final BigDecimal ANOTHER_TOTAL_PRICE = BigDecimal.valueOf(53425);
  private static final String FIRST_DATE = "2150-07-22";
  private static final String SECOND_DATE = "2150-07-24";

  private ChefReport chefReport;

  @BeforeEach
  public void setUpChefReport() {
    chefReport = new ChefReport();
  }

  @Test
  public void whenAddingReportInformation_thenReportInformationAreAddedChronologically() {
    // when
    chefReport.addChefReportInformation(FIRST_DATE, SOME_CHEFS, A_TOTAL_PRICE);
    chefReport.addChefReportInformation(SECOND_DATE, SOME_CHEFS, A_TOTAL_PRICE);

    // then
    assertThat(chefReport.getChefReportInformations().get(0).getDate()).isEqualTo(FIRST_DATE);
    assertThat(chefReport.getChefReportInformations().get(1).getDate()).isEqualTo(SECOND_DATE);
  }

  @Test
  public void givenAListOfReportInformation_whenGetTotalPrice_thenShouldReturnTheRightNumber() {
    // given
    chefReport.addChefReportInformation(FIRST_DATE, SOME_CHEFS, A_TOTAL_PRICE);
    chefReport.addChefReportInformation(FIRST_DATE, SOME_CHEFS, ANOTHER_TOTAL_PRICE);

    // when
    BigDecimal actualPrice = chefReport.getTotalChefPrice();

    // when
    BigDecimal expectedPrice = A_TOTAL_PRICE.add(ANOTHER_TOTAL_PRICE);
    assertThat(actualPrice).isEqualTo(expectedPrice);


  }

}
