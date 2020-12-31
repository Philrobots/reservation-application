package ca.ulaval.glo4002.reservation.api.report.presenter.total;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.reservation.api.report.dto.TotalReportDto;
import ca.ulaval.glo4002.reservation.api.report.presenter.IngredientReportInformationDto;
import ca.ulaval.glo4002.reservation.domain.fullcourse.IngredientName;
import ca.ulaval.glo4002.reservation.domain.report.IngredientReport;
import ca.ulaval.glo4002.reservation.domain.report.IngredientReportInformation;

@ExtendWith(MockitoExtension.class)
public class TotalIngredientReportDtoFactoryTest {
  private static final BigDecimal TOTAL_PRICE = BigDecimal.valueOf(4324.8234);
  private static final BigDecimal PEPPERONI_QUANTITY = BigDecimal.valueOf(7654.235);
  private static final BigDecimal PEPPERONI_PRICE = BigDecimal.valueOf(312.546);
  private static final BigDecimal KIMCHI_QUANTITY = BigDecimal.valueOf(98723.4);
  private static final BigDecimal KIMCHI_PRICE = BigDecimal.valueOf(123);

  @Mock
  private IngredientReport ingredientReport;

  private TotalReportDtoFactory totalReportDtoFactory;

  @BeforeEach
  public void setUp() {
    totalReportDtoFactory = new TotalReportDtoFactory();
  }

  @Test
  public void givenAReport_whenCreate_thenTotalIngredientReportInformationAreGenerated() {
    // when
    totalReportDtoFactory.create(ingredientReport);

    // then
    verify(ingredientReport).generateTotalIngredientReportInformation();
  }

  @Test
  public void givenAReport_whenCreate_thenTotalPriceIsCalculated() {
    // when
    totalReportDtoFactory.create(ingredientReport);

    // then
    verify(ingredientReport).calculateTotalPriceForEntireReport();
  }

  @Test
  public void givenAReport_whenCreate_thenTotalPriceIsCorrectlyCalculated() {
    // given
    given(ingredientReport.calculateTotalPriceForEntireReport()).willReturn(TOTAL_PRICE);

    // when
    TotalReportDto totalReportDto = totalReportDtoFactory.create(ingredientReport);

    // when
    assertThat(totalReportDto.getTotalPrice()).isEquivalentAccordingToCompareTo(TOTAL_PRICE);
  }

  @Test
  public void givenReport_whenCreate_thenIngredientReportInformationDtosAreCorrectlyAssembled() {
    // given
    given(ingredientReport.generateTotalIngredientReportInformation()).willReturn(givenIngredientReportInformation());
    List<IngredientReportInformationDto> expectedIngredientReportInformationDto = givenExpectedIngredientsReportInformationDtos();

    // then
    TotalReportDto totalReportDto = totalReportDtoFactory.create(ingredientReport);

    // then
    assertThat(totalReportDto.getIngredients()).containsExactlyElementsIn(expectedIngredientReportInformationDto);
  }

  @Test
  public void givenReport_whenCreate_thenIngredientReportInformationsDtosAreInAlphabeticalOrder() {
    // given
    given(ingredientReport.generateTotalIngredientReportInformation()).willReturn(givenIngredientReportInformation());

    // then
    TotalReportDto totalReportDto = totalReportDtoFactory.create(ingredientReport);

    // then
    assertThat(totalReportDto.getIngredients()
                             .get(0)
                             .getIngredientName()).isEqualTo(IngredientName.KIMCHI.toString());
    assertThat(totalReportDto.getIngredients()
                             .get(1)
                             .getIngredientName()).isEqualTo(IngredientName.PEPPERONI.toString());
  }

  private Map<IngredientName, IngredientReportInformation> givenIngredientReportInformation() {
    Map<IngredientName, IngredientReportInformation> ingredientNameToIngredientReportInformation = new HashMap<>();
    IngredientReportInformation anIngredientReportInformation = new IngredientReportInformation(IngredientName.PEPPERONI,
                                                                                                PEPPERONI_QUANTITY,
                                                                                                PEPPERONI_PRICE);
    IngredientReportInformation anotherIngredientReportInformation = new IngredientReportInformation(IngredientName.KIMCHI,
                                                                                                     KIMCHI_QUANTITY,
                                                                                                     KIMCHI_PRICE);
    ingredientNameToIngredientReportInformation.put(IngredientName.PEPPERONI,
                                                    anIngredientReportInformation);
    ingredientNameToIngredientReportInformation.put(IngredientName.KIMCHI,
                                                    anotherIngredientReportInformation);
    return ingredientNameToIngredientReportInformation;
  }

  private List<IngredientReportInformationDto> givenExpectedIngredientsReportInformationDtos() {
    IngredientReportInformationDto pepperoniIngredientReportDto = new IngredientReportInformationDto(IngredientName.PEPPERONI.toString(),
                                                                                                     PEPPERONI_QUANTITY,
                                                                                                     PEPPERONI_PRICE);
    IngredientReportInformationDto kimchiIngredientReportDto = new IngredientReportInformationDto(IngredientName.KIMCHI.toString(),
                                                                                                  KIMCHI_QUANTITY,
                                                                                                  KIMCHI_PRICE);
    return Arrays.asList(pepperoniIngredientReportDto, kimchiIngredientReportDto);
  }
}
