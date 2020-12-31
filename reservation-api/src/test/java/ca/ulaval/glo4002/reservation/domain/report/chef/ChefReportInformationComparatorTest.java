package ca.ulaval.glo4002.reservation.domain.report.chef;

import static com.google.common.truth.Truth.assertThat;

import java.math.BigDecimal;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.reservation.domain.chef.Chef;
import ca.ulaval.glo4002.reservation.domain.chef.ChefPriority;
import ca.ulaval.glo4002.reservation.domain.reservation.RestrictionType;

class ChefReportInformationComparatorTest {

  private static final ChefPriority A_CHEF_TYPE = ChefPriority.FIRST;
  private static final String A_CHEF_NAME = "Thierry Aki";
  private static final Set<RestrictionType> SOME_SPECIALTIES = Set.of(RestrictionType.NONE);
  private static final Chef A_CHEF = new Chef(A_CHEF_NAME, A_CHEF_TYPE, SOME_SPECIALTIES);

  private static final Set<Chef> SOME_CHEFS = Set.of(A_CHEF);
  private static final BigDecimal A_TOTAL_PRICE = BigDecimal.TEN;
  private ChefReportInformationComparator comparator;

  @BeforeEach
  public void setUpComparator() {
    comparator = new ChefReportInformationComparator();
  }

  @Test
  public void givenChefReportInformationWithSameDates_whenCompare_thenReturnZero() {
    // given
    String date = "2020-12-12";
    ChefReportInformation chefReportInformation = givenAChefReportInformationWithDate(date);

    // when
    int result = comparator.compare(chefReportInformation, chefReportInformation);

    // then
    assertThat(result).isEqualTo(0);
  }

  @Test
  public void givenFirstChefReportInformationWithEarlierDate_whenCompare_thenReturnNegativeOne() {
    // given
    String earlierDate = "2020-12-12";
    String laterDate = "2021-12-12";
    ChefReportInformation earlierChefReportInformation = givenAChefReportInformationWithDate(earlierDate);
    ChefReportInformation laterChefReportInformation = givenAChefReportInformationWithDate(laterDate);

    // when
    int result = comparator.compare(earlierChefReportInformation, laterChefReportInformation);

    // then
    assertThat(result).isEqualTo(-1);
  }

  @Test
  public void givenFirstChefReportInformationWithLaterDate_whenCompare_thenReturnNegativeOne() {
    // given
    String earlierDate = "2020-12-12";
    String laterDate = "2021-12-12";
    ChefReportInformation earlierChefReportInformation = givenAChefReportInformationWithDate(earlierDate);
    ChefReportInformation laterChefReportInformation = givenAChefReportInformationWithDate(laterDate);

    // when
    int result = comparator.compare(laterChefReportInformation, earlierChefReportInformation);

    // then
    assertThat(result).isEqualTo(1);
  }

  private ChefReportInformation givenAChefReportInformationWithDate(String date) {
    return new ChefReportInformation(SOME_CHEFS, date, A_TOTAL_PRICE);
  }

}
