package ca.ulaval.glo4002.reservation.api.report.assembler;

import static com.google.common.truth.Truth.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.reservation.api.report.dto.ChefReportDto;
import ca.ulaval.glo4002.reservation.domain.chef.Chef;
import ca.ulaval.glo4002.reservation.domain.chef.ChefPriority;
import ca.ulaval.glo4002.reservation.domain.report.chef.ChefReport;
import ca.ulaval.glo4002.reservation.domain.reservation.RestrictionType;

public class ChefReportDtoAssemblerTest {
  private static final String A_DATE = "2020-02-10";
  private static final String ANOTHER_DATE = "2040-02-10";
  private static final BigDecimal A_TOTAL_PRICE = BigDecimal.TEN;
  private static final BigDecimal ANOTHER_TOTAL_PRICE = BigDecimal.ONE;

  private static final ChefPriority A_CHEF_TYPE = ChefPriority.SECOND;
  private static final String A_CHEF_NAME = "Bob Smarties";
  private static final Set<RestrictionType> SOME_SPECIALTIES = Set.of(RestrictionType.VEGAN);

  private static final ChefPriority ANOTHER_CHEF_TYPE = ChefPriority.NINTH;
  private static final String ANOTHER_CHEF_NAME = "Amélie Mélo";
  private static final Set<RestrictionType> SOME_OTHER_SPECIALTIES = Set.of(RestrictionType.ALLERGIES,
                                                                            RestrictionType.VEGAN);

  private ChefReportDtoAssembler chefReportDtoAssembler;

  private Chef aChef;
  private Chef anotherChef;

  @BeforeEach
  public void setUpChefReportDtoAssembler() {
    chefReportDtoAssembler = new ChefReportDtoAssembler();
    aChef = new Chef(A_CHEF_NAME, A_CHEF_TYPE, SOME_SPECIALTIES);
    anotherChef = new Chef(ANOTHER_CHEF_NAME, ANOTHER_CHEF_TYPE, SOME_OTHER_SPECIALTIES);
  }

  @Test
  public void givenChefsHiredOnTwoDates_whenAssembleChefReportDto_thenDtoHasMultipleChefInformationDtos() {
    // given
    ChefReport chefReport = givenAChefReportWithChefsHiredOnTwoDates();

    // when
    ChefReportDto chefReportDto = chefReportDtoAssembler.assembleChefReportDto(chefReport);

    // then
    assertThat(chefReportDto.getChefsReportInformationDto()
                            .size()).isEqualTo(chefReport.getChefReportInformations().size());
  }

  @Test
  public void givenChefsHiredOnOneDate_whenAssembleChefReportDto_thenDtoHasTheRightDate() {
    // given
    ChefReport chefReport = givenAChefReportWithChefsHiredOnOneDate();
    String expectedDate = chefReport.getChefReportInformations().get(0).getDate();

    // when
    ChefReportDto chefReportDto = chefReportDtoAssembler.assembleChefReportDto(chefReport);

    // then
    assertThat(chefReportDto.getChefsReportInformationDto()
                            .get(0)
                            .getDate()).isEqualTo(expectedDate);
  }

  @Test
  public void givenChefsHiredOnOneDate_whenAssembleChefReportDto_thenDtoHasTheRightPrice() {
    // given
    ChefReport chefReport = givenAChefReportWithChefsHiredOnOneDate();
    BigDecimal expectedTotalPrice = chefReport.getChefReportInformations().get(0).getTotalPrice();

    // when
    ChefReportDto chefReportDto = chefReportDtoAssembler.assembleChefReportDto(chefReport);

    // then
    assertThat(chefReportDto.getChefsReportInformationDto()
                            .get(0)
                            .getTotalPrice()).isEqualTo(expectedTotalPrice);
  }

  @Test
  public void whenAssembleChefReportDto_thenChefsAreInAlphabeticalOrder() {
    // given
    ChefReport chefReport = new ChefReport();
    Chef firstChef = new Chef("B", ChefPriority.FIRST, Set.of(RestrictionType.NONE));
    Chef secondChef = new Chef("A", ChefPriority.FIRST, Set.of(RestrictionType.NONE));
    chefReport.addChefReportInformation(A_DATE, Set.of(firstChef, secondChef), A_TOTAL_PRICE);
    List<String> expectedSortedChefs = List.of("A", "B");

    // when
    ChefReportDto chefReportDto = chefReportDtoAssembler.assembleChefReportDto(chefReport);

    // then
    assertThat(chefReportDto.getChefsReportInformationDto()
                            .get(0)
                            .getChefs()).isEqualTo(expectedSortedChefs);
  }

  @Test
  public void givenChefsHiredOnOneDate_whenAssembleChefReportDto_thenDtoHasRightNumberOfChefs() {
    // given
    ChefReport chefReport = givenAChefReportWithMultipleChefs();
    Set<Chef> expectedChefs = chefReport.getChefReportInformations().get(0).getChefs();

    // when
    ChefReportDto chefReportDto = chefReportDtoAssembler.assembleChefReportDto(chefReport);

    // then
    List<String> actualChefsName = chefReportDto.getChefsReportInformationDto().get(0).getChefs();
    assertThat(actualChefsName.size()).isEqualTo(expectedChefs.size());
  }

  private ChefReport givenAChefReportWithChefsHiredOnOneDate() {
    ChefReport chefReport = new ChefReport();
    chefReport.addChefReportInformation(A_DATE, Set.of(aChef), A_TOTAL_PRICE);
    return chefReport;
  }

  private ChefReport givenAChefReportWithMultipleChefs() {
    ChefReport chefReport = new ChefReport();
    chefReport.addChefReportInformation(A_DATE, Set.of(aChef, anotherChef), A_TOTAL_PRICE);
    return chefReport;
  }

  private ChefReport givenAChefReportWithChefsHiredOnTwoDates() {
    ChefReport chefReport = new ChefReport();
    chefReport.addChefReportInformation(A_DATE, Set.of(aChef), A_TOTAL_PRICE);
    chefReport.addChefReportInformation(ANOTHER_DATE, Set.of(aChef), ANOTHER_TOTAL_PRICE);
    return chefReport;
  }

}
